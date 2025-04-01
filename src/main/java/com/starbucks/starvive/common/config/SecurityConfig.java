package com.starbucks.starvive.common.config;

import com.starbucks.starvive.common.jwt.JwtAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.HttpStatus;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider oAuthAuthenticationProvider;
    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(List.of("Authorization, Content-Type, X-CSRF-TOKEN", "Set-Cookie"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> 
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                "/api/v1/auth/**",
                    "/api/v1/size/**",
                    "/api/v1/color/**",
                    "/api/v1/products/**",
                    "/api/v1/category/**",
                    "/api/v1/vendor/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error",
                    "/api/users/signin",
                    "/api/users/signup",
                    "/api/users/refresh"
                    )
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/review/**")
                .permitAll()
                .requestMatchers("/api/users/signout")
                .authenticated()
                .anyRequest()
                .authenticated())
            .sessionManagement((sessionManagement) -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(this.daoAuthenticationProvider)
            .authenticationProvider(this.oAuthAuthenticationProvider)
            .addFilterBefore(this.jwtAuthenticationFilter,  
                UsernamePasswordAuthenticationFilter.class)
            .addFilter(this.corsFilter())
            .exceptionHandling(exceptions -> 
                exceptions.authenticationEntryPoint(unauthorizedEntryPoint())
            );
        return http.build();
    }

    public SecurityConfig(
            final AuthenticationProvider oAuthAuthenticationProvider,
            final AuthenticationProvider daoAuthenticationProvider,
            final JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.oAuthAuthenticationProvider = oAuthAuthenticationProvider;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
}
