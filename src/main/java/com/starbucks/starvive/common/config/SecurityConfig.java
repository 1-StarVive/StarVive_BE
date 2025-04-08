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
        // 요청을 허용할 출처 패턴 설정 (모든 출처 허용)
        config.addAllowedOriginPattern("*");
        // 허용할 헤더 (모든 헤더 허용)
        config.addAllowedHeader("*");
        // 허용할 HTTP 메서드 (모든 메서드 허용)
        config.addAllowedMethod("*");
        // 브라우저(프론트엔드)에서 접근 가능하도록 노출할 응답 헤더 설정
        config.setExposedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN", "Set-Cookie"));

        // 위에서 만든 CORS 설정을 모든 경로("/**")에 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 인증 실패 시 처리 (401 Unauthorized 응답)
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }

    // Spring Security 필터 체인 설정, 이거는 어디에서 온건가 검증증
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF(Cross-Site Request Forgery) 보호 비활성화 (Stateless API 서버이므로)
            .csrf(AbstractHttpConfigurer::disable)
            // 위에서 정의한 CORS 설정 적용
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 세션 관리 정책 설정: STATELESS (세션을 사용하지 않음)
            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // HTTP 요청에 대한 인가(Authorization) 규칙 설정
            .authorizeHttpRequests(auth -> auth
                // 특정 경로들은 인증 없이 접근 허용 (permitAll)
                .requestMatchers(
                    "/api/users/signin",
                    "/api/users/signup",
                    "/api/users/refresh",
                    "/login/oauth2/code/**", 
                    "/oauth2/**", 
                    "/swagger-ui/**", 
                    "/v3/api-docs/**", 
                    "/error",
                    "/api/v1/**",
                    "/api/v1/auth/**", 
                    "/api/v1/size/**", 
                    "/api/v1/color/**", 
                    "/api/v1/products/**", 
                    "/api/v1/category/**", 
                    "/api/v1/vendor/**" ,
                    "/api/v1/topCategories/**",
                    "/api/v1/middleCategories/**",
                    "/api/v1/bottomCategories/**"
                ).permitAll()
                // GET 요청 중 /api/v1/review/** 경로는 인증 없이 허용
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                // POST 요청 중 /api/users/signout 경로는 인증 필요
                .requestMatchers("/api/users/signout").authenticated()
                // 위에서 명시적으로 설정된 경로 외의 모든 요청은 인증(authenticated) 필요
                .anyRequest().authenticated()
            )
            // OAuth2 로그인 관련 설정
            .oauth2Login(oauth2 -> oauth2
                // 사용자 정보를 가져오는 엔드포인트 관련 설정
                .userInfoEndpoint(userInfo -> userInfo
                    // 커스텀 OAuth2 사용자 서비스 지정
                    .userService(customOAuth2UserService)
                )
                // OAuth2 인증 성공 시 실행될 핸들러 지정
                .successHandler(oAuth2AuthenticationSuccessHandler)
            )
            // 사용할 인증 제공자(AuthenticationProvider) 설정 (ApplicationConfig에서 정의된 DaoAuthenticationProvider)
            .authenticationProvider(daoAuthenticationProvider)
            // 직접 구현한 JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // 예외 처리 설정: 인증되지 않은 접근(AuthenticationEntryPoint) 처리
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(unauthorizedEntryPoint())
            );
        return http.build();
    }
}
