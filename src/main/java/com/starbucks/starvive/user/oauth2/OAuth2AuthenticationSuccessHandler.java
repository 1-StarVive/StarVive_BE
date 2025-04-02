package com.starbucks.starvive.user.oauth2;

import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.user.domain.RefreshToken;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.repository.RefreshTokenRepository;
import com.starbucks.starvive.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

// 소셜 로그인 인증 성공 후 처리하는 클래스스
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 리다이렉트 URL 설정
    @Value("${oauth2.redirect.url:http://localhost:3000/auth/callback}") 
    private String redirectUrl;

    // 인증 성공 시 처리
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            log.error("Authentication is not an instance of OAuth2AuthenticationToken");
            super.onAuthenticationSuccess(request, response, authentication); 
        }
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = oauthToken.getPrincipal();

        String email = extractEmailBasedOnProvider(oAuth2User, registrationId);
        if (email == null) {
             log.error("Could not extract email from OAuth2User for provider: {}", registrationId);
             // 에러 페이지로 리다이렉트
             getRedirectStrategy().sendRedirect(request, response, "/error?message=oauth_email_error"); 
             return;
        }

        // 유저 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OAuth2 authenticated user not found in DB (unexpected): " + email));

        // 유저의 UUID를 기반으로 시스템 JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());

        // 리프레시 토큰 저장
        saveRefreshToken(user, refreshToken);

        // 토큰을 포함한 리다이렉트 URL 생성
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        log.info("Redirecting OAuth2 user {} to {}", email, redirectUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 리프레시 토큰 저장
    private void saveRefreshToken(User user, String refreshToken) {
        Instant expiryDate = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationTime());
        RefreshToken refreshTokenEntity = RefreshToken.builder()
            .token(refreshToken)
            .userId(user.getUserId())
            .expiryDate(expiryDate)
            .build();

        // Delete existing token and save the new one
        refreshTokenRepository.deleteByUserId(user.getUserId()); 
        refreshTokenRepository.save(refreshTokenEntity);
        log.info("Saved refresh token for user: {}", user.getEmail());
    }

    // Helper to extract email based on provider specifics
    private String extractEmailBasedOnProvider(OAuth2User oAuth2User, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return oAuth2User.getAttribute("email");
        } else if ("kakao".equalsIgnoreCase(registrationId)) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            if (kakaoAccount != null) {
                return (String) kakaoAccount.get("email");
            }
        }
        log.warn("Email extraction not implemented for provider: {}", registrationId);
        return null;
    }
} 