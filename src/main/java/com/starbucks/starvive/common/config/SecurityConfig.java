package com.starbucks.starvive.common.config;

import com.starbucks.starvive.common.jwt.JwtAuthenticationFilter;
import com.starbucks.starvive.user.oauth2.CustomOAuth2UserService;
import com.starbucks.starvive.user.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CORS 설정을 담을 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 자격 증명(쿠키 등)을 포함한 요청을 허용 여부
        config.setAllowCredentials(true);
        // 요청을 허용할 출처 패턴 설정정
        config.addAllowedOriginPattern("*");
        // 허용할 헤더
        config.addAllowedHeader("*");
        // 허용할 HTTP메서드
        config.addAllowedMethod("*");
        // 브라우저(프론트엔드)에서 접근 가능하도록 노출할 응답 헤더 설정
        config.setExposedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN", "Set-Cookie"));

        // 위에서 만든 CORS 설정을 모든 경로에 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 인증 실패 시 처리
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> 
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }

    // 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 보호 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // CORS 설정 적용
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 세션 관리 설정
            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 인증 요청 처리
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    // 허용할 경로
        "/api/users/signin",
                    "/api/users/signup",
                    "/api/users/refresh",
                    "/login/oauth2/code/**",
                    "/oauth2/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error",
                    "/api/v1/auth/**",
                    "/api/v1/size/**",
                    "/api/v1/color/**",
                    "/api/v1/products/**",
                    "/api/v1/category/**",
                    "/api/v1/vendor/**"
                ).permitAll()
                // GET 요청 중 /api/v1/review/** 경로는 인증 없이 허용
                .requestMatchers(HttpMethod.GET, "/api/v1/review/**").permitAll()
                // POST 요청 중 /api/users/signout 경로는 인증 필요
                .requestMatchers("/api/users/signout").authenticated()
                // 나머지 요청은 인증 필요
                .anyRequest().authenticated()
            )
            // OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                // 사용자 정보 엔드포인트 설정
                .userInfoEndpoint(userInfo -> userInfo
                    // 사용자 서비스 설정
                    .userService(customOAuth2UserService)
                )
                // 인증 성공 시 처리
                .successHandler(oAuth2AuthenticationSuccessHandler)
            )
            // 인증 제공자 설정
            .authenticationProvider(daoAuthenticationProvider)
            // JWT 필터 적용
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // 인증 실패 시 처리
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(unauthorizedEntryPoint())
            );
        return http.build();
    }
}
