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

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

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

        User user = findOrCreateUser(socialLoginType, socialId, email, name);
        if (user == null) {
             log.error("소셜 로그인 중 사용자 조회 또는 생성/연동 실패: {} / {}", socialLoginType, socialId);
             sendErrorResponse(response, "사용자 처리 오류");
             return;
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
        long expiresIn = jwtTokenProvider.getAccessTokenExpirationTime();

        saveRefreshToken(user, refreshToken);

        SignInResponseDto responseDto = SignInResponseDto.from(user, accessToken, refreshToken, expiresIn);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), responseDto);

        log.info("Successfully processed OAuth2 로그인 처리 완료: {} / {}", socialLoginType, user.getUserId());
    }

    private User findOrCreateUser(SocialLoginType socialLoginType, String socialId, String email, String name) {
        Optional<User> userOptional = userRepository.findBySocialLoginTypeAndSocialId(socialLoginType, socialId);

        if (userOptional.isPresent()) {
            log.info("소셜 로그인 중 사용자 존재: {} / {}", socialLoginType, socialId);
            return userOptional.get();
        } else {
            
            Optional<User> emailUserOptional = Optional.empty();
            if (email != null) {
                 emailUserOptional = userRepository.findByEmail(email);
            }

            if (emailUserOptional.isPresent()) {
               
                log.info("이메일로 기존 사용자 존재: {} / {}", email, socialLoginType, socialId);
                User existingUser = emailUserOptional.get();

                existingUser.updateSocialInfo(socialLoginType, socialId);
                return existingUser; 

            } else {
                log.info("소셜 로그인 중 신규 사용자 생성: {} / {}", socialLoginType, socialId);

                 String finalEmail = email;
                 if (finalEmail == null || userRepository.existsByEmail(finalEmail)) {
                     finalEmail = socialLoginType.toString().toLowerCase() + "_" + socialId + "@social.local"; 
                     log.warn("임시 이메일 생성: {} / {}", finalEmail);
                 }

                 String finalNickname = (name != null && !name.isBlank()) ? name : NicknameGenerator.generateRandomNickname();

                User newUser = User.builder()
                        .loginId(null) 
                            .email(finalEmail) 
                            .password(null) 
                        .name(name != null ? name : "사용자") 
                        .socialId(socialId)
                        .nickname(finalNickname) 
                        .phoneNumber(null)
                        .birth(null) 
                        .gender(Gender.OTHER) 
                        .socialLoginType(socialLoginType) 
                        .status(UserStatus.ACTIVE) 
                        .build();
                try {
                     return userRepository.save(newUser);
                 } catch (Exception e) {
                     log.error("소셜 로그인 중 신규 사용자 저장 실패: {} / {}", socialLoginType, socialId, e);
                     
                     return null;
                 }
            }
        }
    }

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

    private void sendErrorResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorDetails = Map.of("error", "OAuth2 인증 실패", "errorCode", errorCode);
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
} 