package com.starbucks.starvive.user.oauth2;

import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.user.domain.*;
import com.starbucks.starvive.user.repository.RefreshTokenRepository;
import com.starbucks.starvive.user.repository.UserRepository;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.starbucks.starvive.common.utils.NicknameGenerator;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

// 소셜 로그인 인증 성공 후 처리하는 클래스
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

    // 리다이렉트 URL 설정
    // @Value("${oauth2.redirect.url:http://localhost:3000/auth/callback}")
    // private String redirectUrl;

    // 인증 성공 시 처리
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            log.error("인증 타입이 OAuth2AuthenticationToken이 아님");
            sendErrorResponse(response, "인증 타입 오류");
            return;
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = oauthToken.getPrincipal();

        // 1. Provider 정보와 소셜 ID, 이메일, 이름 추출
        SocialLoginType socialLoginType;
        try {
            socialLoginType = SocialLoginType.valueOf(registrationId.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("지원하지 않는 프로바이더: {}", registrationId);
            sendErrorResponse(response, "지원하지 않는 프로바이더");
            return;
        }

        String socialId = extractSocialIdBasedOnProvider(oAuth2User, registrationId);
        String email = extractEmailBasedOnProvider(oAuth2User, registrationId);
        String name = extractNameBasedOnProvider(oAuth2User, registrationId);

        if (socialId == null) {
            log.error("OAuth2User에서 소셜ID 추출 실패: {}", registrationId);
            sendErrorResponse(response, "소셜ID 추출 실패");
            return;
        }

        // 2. 소셜 정보로 사용자 조회 또는 생성/연동
        User user = findOrCreateUser(socialLoginType, socialId, email, name);
        if (user == null) {
             log.error("소셜 로그인 중 사용자 조회 또는 생성/연동 실패: {} / {}", socialLoginType, socialId);
             sendErrorResponse(response, "사용자 처리 오류");
             return;
        }

        // 3. JWT 토큰 생성 및 만료 시간 가져오기
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
        long expiresIn = jwtTokenProvider.getAccessTokenExpirationTime();

        // 4. 리프레시 토큰 저장
        saveRefreshToken(user, refreshToken);

        // 5. JSON 응답 생성 및 전송 (리다이렉트 대신)
        SignInResponseDto responseDto = SignInResponseDto.from(user, accessToken, refreshToken, expiresIn);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), responseDto);

        log.info("Successfully processed OAuth2 로그인 처리 완료: {} / {}", socialLoginType, user.getUserId());
    }

    // 소셜 정보 기반으로 사용자 조회/생성/연동 처리
    private User findOrCreateUser(SocialLoginType socialLoginType, String socialId, String email, String name) {
        // 1. 소셜 타입과 소셜 ID로 사용자 조회
        Optional<User> userOptional = userRepository.findBySocialLoginTypeAndSocialId(socialLoginType, socialId);

        if (userOptional.isPresent()) {
            // 1-1. 소셜 정보로 사용자가 이미 존재하면 해당 사용자 반환
            log.info("소셜 로그인 중 사용자 존재: {} / {}", socialLoginType, socialId);
            return userOptional.get();
        } else {
            // 1-2. 소셜 정보로 사용자가 없으면 이메일로 기존 사용자 조회 시도
            Optional<User> emailUserOptional = Optional.empty();
            if (email != null) {
                 emailUserOptional = userRepository.findByEmail(email);
            }

            if (emailUserOptional.isPresent()) {
                // 이메일로 기존 사용자가 존재하면 해당 사용자에 소셜 정보 연동(업데이트)
                log.info("이메일로 기존 사용자 존재: {} / {}", email, socialLoginType, socialId);
                User existingUser = emailUserOptional.get();

                existingUser.updateSocialInfo(socialLoginType, socialId);
                return existingUser; // 업데이트된 기존 사용자 반환

            } else {
                // 2-2. 이메일로도 사용자가 없으면 신규 사용자 생성
                log.info("소셜 로그인 중 신규 사용자 생성: {} / {}", socialLoginType, socialId);

                // 이메일이 없거나 중복될 경우를 대비한 임시 이메일 생성 로직 추가
                 String finalEmail = email;
                 if (finalEmail == null || userRepository.existsByEmail(finalEmail)) {
                     finalEmail = socialLoginType.toString().toLowerCase() + "_" + socialId + "@social.local"; // 고유성 보장을 위한 임시 이메일
                     log.warn("임시 이메일 생성: {} / {}", finalEmail);
                 }

                // 닉네임 설정: 이름이 있으면 이름을 사용, 없으면 랜덤 닉네임 생성
                String finalNickname = (name != null && !name.isBlank()) ? name : NicknameGenerator.generateRandomNickname();

                User newUser = User.builder()
                        .loginId(null) // 소셜 로그인이므로 일반 loginId 없음
                        .email(finalEmail) // 최종 결정된 이메일 사용
                        .password(null) // 소셜 로그인이므로 비밀번호 없음
                        .name(name != null ? name : "사용자") // 이름 없으면 기본값 
                        .socialId(socialId) // 소셜 ID 설정
                        .nickname(finalNickname) // 최종 결정된 닉네임 사용 (랜덤 또는 제공된 이름)
                        .phoneNumber(null)
                        .birth(null) // 생년월일 등 추가 정보는 필요시 별도 수집
                        .gender(Gender.OTHER) // UNKNOWN 대신 OTHER 사용 (사용자 확인)
                        .socialLoginType(socialLoginType) // 소셜 타입 설정
                        .status(UserStatus.ACTIVE) // 사용자 상태 활성
                        .build();
                try {
                     return userRepository.save(newUser);
                 } catch (Exception e) {
                     log.error("소셜 로그인 중 신규 사용자 저장 실패: {} / {}", socialLoginType, socialId, e);
                     // 데이터 저장 중 예외 발생 시 (예: 예상치 못한 DB 제약 조건 위반) null 반환
                     return null;
                 }
            }
        }
    }

    // 리프레시 토큰 저장
    private void saveRefreshToken(User user, String refreshToken) {
        Instant expiryDate = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationTime());
        RefreshToken refreshTokenEntity = RefreshToken.builder()
            .token(refreshToken)
            .userId(user.getUserId())
            .expiryDate(expiryDate)
            .build();

        refreshTokenRepository.deleteByUserId(user.getUserId());
        refreshTokenRepository.save(refreshTokenEntity);
        log.info("리프레시 토큰 저장 완료: {} / {}", user.getSocialLoginType(), user.getUserId());
    }

    // Provider별 소셜 ID 추출
    private String extractSocialIdBasedOnProvider(OAuth2User oAuth2User, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return oAuth2User.getAttribute("sub");
        } else if ("kakao".equalsIgnoreCase(registrationId)) {
             Object idObj = oAuth2User.getAttribute("id");
             return idObj != null ? String.valueOf(idObj) : null;
        }
        log.warn("소셜 ID 추출 지원 안됨: {}", registrationId);
        return null;
    }

    // Provider별 이메일 추출
    private String extractEmailBasedOnProvider(OAuth2User oAuth2User, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return oAuth2User.getAttribute("email");
        } else if ("kakao".equalsIgnoreCase(registrationId)) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            if (kakaoAccount != null &&
                Boolean.TRUE.equals(kakaoAccount.get("has_email")) &&
                Boolean.FALSE.equals(kakaoAccount.get("email_needs_agreement")) &&
                Boolean.TRUE.equals(kakaoAccount.get("is_email_valid")) &&
                Boolean.TRUE.equals(kakaoAccount.get("is_email_verified"))) {
                return (String) kakaoAccount.get("email");
            }
        }
        log.warn("이메일 추출 지원 안됨: {}", registrationId);
        return null;
    }

    // Provider별 이름 추출
    private String extractNameBasedOnProvider(OAuth2User oAuth2User, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return oAuth2User.getAttribute("name");
        } else if ("kakao".equalsIgnoreCase(registrationId)) {
             Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
             if(kakaoAccount != null) {
                 Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                 if(profile != null) {
                     return (String) profile.getOrDefault("nickname", null);
                 }
             }
        }
        log.warn("이름 추출 지원 안됨: {}", registrationId);
        return null;
    }

    // 에러 응답 전송 (JSON 형태)
    private void sendErrorResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorDetails = Map.of("error", "OAuth2 인증 실패", "errorCode", errorCode);
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
} 