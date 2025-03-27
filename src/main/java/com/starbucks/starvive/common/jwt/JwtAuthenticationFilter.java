package com.starbucks.starvive.common.jwt;

import com.starbucks.starvive.user.infrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            log.debug("Received JWT token: {}", jwt);

            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                log.debug("JWT token is valid");
                String userId = jwtTokenProvider.validateAndGetUserUuid(jwt);
                log.debug("Extracted userId from token: {}", userId);
                
                if (userId != null && !userId.isEmpty()) {
                    // UUID로 사용자를 찾아서 이메일을 가져옴
                    String email = userRepository.findById(UUID.fromString(userId))
                            .map(user -> user.getEmail())
                            .orElse(null);
                    
                    if (email != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        log.debug("Loaded user details for email: {}", email);
                        
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.debug("Set authentication in SecurityContextHolder");
                    } else {
                        log.warn("No user found for userId: {}", userId);
                    }
                } else {
                    log.warn("No userId found in token");
                }
            } else {
                log.warn("JWT token is invalid or null");
            }
        } catch (Exception e) {
            log.error("Error in JwtAuthenticationFilter", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
