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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
            log.error("Authentication is not an instance of OAuth2AuthenticationToken");
            sendErrorResponse(response, "invalid_authentication_type");
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
            log.error("Unsupported registrationId: {}", registrationId);
            sendErrorResponse(response, "unsupported_provider");
            return;
        }

        String socialId = extractSocialIdBasedOnProvider(oAuth2User, registrationId);
        String email = extractEmailBasedOnProvider(oAuth2User, registrationId);
        String name = extractNameBasedOnProvider(oAuth2User, registrationId);

        if (socialId == null) {
            log.error("Could not extract socialId from OAuth2User for provider: {}", registrationId);
            sendErrorResponse(response, "oauth_socialid_error");
            return;
        }

        // 2. 소셜 정보로 사용자 조회 또는 생성/연동
        User user = findOrCreateUser(socialLoginType, socialId, email, name);
        if (user == null) {
             log.error("Failed to find or create user for social login: {} / {}", socialLoginType, socialId);
             sendErrorResponse(response, "user_processing_error");
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

        log.info("Successfully processed OAuth2 login for user ({} / {}), returning tokens in response body.", socialLoginType, user.getUserId());
        // clearAuthenticationAttributes(request); // 필요 시 이전 인증 속성 제거
    }

    // 소셜 정보 기반으로 사용자 조회/생성/연동 처리
    private User findOrCreateUser(SocialLoginType socialLoginType, String socialId, String email, String name) {
        // 1. 소셜 타입과 소셜 ID로 사용자 조회
        Optional<User> userOptional = userRepository.findBySocialLoginTypeAndSocialId(socialLoginType, socialId);

        if (userOptional.isPresent()) {
            // 1-1. 소셜 정보로 사용자가 이미 존재하면 해당 사용자 반환
            log.info("Existing social user found: {} / {}", socialLoginType, socialId);
            return userOptional.get();
        } else {
            // 1-2. 소셜 정보로 사용자가 없으면 이메일로 기존 사용자 조회 시도
            Optional<User> emailUserOptional = Optional.empty();
            if (email != null) {
                 emailUserOptional = userRepository.findByEmail(email);
            }

            if (emailUserOptional.isPresent()) {
                // 2-1. 이메일로 기존 사용자가 존재하면 해당 사용자에 소셜 정보 연동(업데이트)
                log.info("Existing user found by email [{}], linking social info: {} / {}", email, socialLoginType, socialId);
                User existingUser = emailUserOptional.get();

                // User 엔티티의 updateSocialInfo 메소드 호출하여 연동
                existingUser.updateSocialInfo(socialLoginType, socialId);
                // JPA 변경 감지(Dirty Checking)에 의해 트랜잭션 종료 시 업데이트되거나,
                // 명시적으로 save 호출 (현재는 @Transactional 범위 내이므로 자동 업데이트 기대)
                // return userRepository.save(existingUser); // 명시적 저장이 필요하면 주석 해제
                return existingUser; // 업데이트된 기존 사용자 반환

            } else {
                // 2-2. 이메일로도 사용자가 없으면 신규 사용자 생성
                log.info("Creating new user for social login: {} / {}", socialLoginType, socialId);

                // 이메일이 없거나 중복될 경우를 대비한 임시 이메일 생성 로직 추가
                 String finalEmail = email;
                 if (finalEmail == null || userRepository.existsByEmail(finalEmail)) {
                     finalEmail = socialLoginType.toString().toLowerCase() + "_" + socialId + "@social.local"; // 고유성 보장을 위한 임시 이메일
                     log.warn("Email not provided or already exists. Using temporary email: {}", finalEmail);
                 }


                User newUser = User.builder()
                        .loginId(null) // 소셜 로그인이므로 일반 loginId 없음
                        .email(finalEmail) // 최종 결정된 이메일 사용
                        .password(null) // 소셜 로그인이므로 비밀번호 없음
                        .name(name != null ? name : "사용자") // 이름 없으면 기본값
                        .socialId(socialId) // 소셜 ID 설정
                        .nickname(null) // 닉네임은 필요시 추가 설정
                        .phoneNumber(null)
                        .birth(null) // 생년월일 등 추가 정보는 필요시 별도 수집
                        .gender(Gender.OTHER) // UNKNOWN 대신 OTHER 사용 (사용자 확인)
                        .socialLoginType(socialLoginType) // 소셜 타입 설정
                        .status(UserStatus.ACTIVE) // 사용자 상태 활성
                        .build();
                try {
                     return userRepository.save(newUser);
                 } catch (Exception e) {
                     log.error("Failed to save new social user: {} / {}", socialLoginType, socialId, e);
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
        log.info("Saved refresh token for user: {} / {}", user.getSocialLoginType(), user.getUserId());
    }

    // Provider별 소셜 ID 추출
    private String extractSocialIdBasedOnProvider(OAuth2User oAuth2User, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return oAuth2User.getAttribute("sub");
        } else if ("kakao".equalsIgnoreCase(registrationId)) {
             Object idObj = oAuth2User.getAttribute("id");
             return idObj != null ? String.valueOf(idObj) : null;
        }
        log.warn("Social ID extraction not implemented for provider: {}", registrationId);
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
        log.warn("Email extraction not supported or available for provider: {}", registrationId);
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
        log.warn("Name extraction not implemented for provider: {}", registrationId);
        return null;
    }

    // 에러 응답 전송 (JSON 형태)
    private void sendErrorResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorDetails = Map.of("error", "OAuth2 Authentication Failed", "errorCode", errorCode);
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
} 