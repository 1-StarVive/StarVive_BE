package com.starbucks.starvive.common.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.starbucks.starvive.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * JWT 인증 필터.
 * HTTP 요청 헤더에서 JWT 토큰을 추출하고 유효성을 검증하여
 * SecurityContext에 인증 정보를 설정합니다.
 * OncePerRequestFilter를 상속받아 요청당 한 번만 실행됨을 보장합니다.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        // 특정 경로들은 필터링을 건너니다 (예: 로그인, 회원가입, Swagger).
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/users/signin") || 
            requestURI.startsWith("/api/users/signup") ||
            requestURI.startsWith("/api/users/refresh") || // Refresh Token 경로도 추가
            requestURI.startsWith("/login/oauth2/code") || // OAuth2 경로 추가
            requestURI.startsWith("/oauth2") || // OAuth2 경로 추가
            requestURI.startsWith("/swagger-ui") || 
            requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response); // 다음 필터로 요청 전달
            return; // 필터 처리 종료
        }

        // "Authorization" 헤더에서 JWT 토큰 추출 시도
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userIdString;

        // 헤더가 없거나 "Bearer "로 시작하지 않으면 필터링 건너니다
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 다음의 문자열(실제 토큰) 추출
        jwt = authHeader.substring(7);
        try {
            // JwtTokenProvider를 사용하여 토큰 검증 및 사용자 ID 추출
            userIdString = jwtTokenProvider.validateAndGetUserUuid(jwt);
        } catch (Exception e) {
            // 토큰 검증 실패 시 401 Unauthorized 응답 반환
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 사용자 ID가 추출되었고, 현재 SecurityContext에 인증 정보가 없는 경우
        if (userIdString != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // 추출된 사용자 ID(문자열)를 UUID로 변환
                UUID userId = UUID.fromString(userIdString);
                // UserRepository를 사용하여 DB에서 사용자 정보 조회
                // User 객체가 UserDetails를 구현한다고 가정
                UserDetails userDetails = (UserDetails) this.userRepository.findByUserId(userId)
                                         .orElse(null); // 사용자를 찾지 못하면 null 반환

                // DB에서 사용자를 성공적으로 찾은 경우
                if (userDetails != null) {
                    // 사용자 정보(UserDetails)를 기반으로 인증 토큰(UsernamePasswordAuthenticationToken) 생성
                    // 비밀번호는 필요 없으므로 null 전달, 권한 정보는 userDetails에서 가져옴
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 요청 관련 상세 정보 설정
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // SecurityContext에 최종 인증 정보(Authentication) 설정
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (IllegalArgumentException e) {
                // UUID 변환 실패 등 예외 발생 시 401 Unauthorized 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        // 다음 필터로 요청/응답 전달
        filterChain.doFilter(request, response);
    }

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }
}