package com.starbucks.starvive.common.jwt;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.function.Function;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.security.core.Authentication;
import javax.crypto.SecretKey;
import com.starbucks.starvive.user.domain.User;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class JwtTokenProvider {

    private final Environment env; 
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
  
    public String validateAndGetUserUuid(String token) throws IllegalArgumentException {
        try {
            return (String)this.extractClaim(token, Claims::getSubject);
        } catch (NullPointerException var3) {
            throw new IllegalArgumentException("토큰에 담긴 유저 정보(Subject)가 없습니다");
        }
    }
    

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userIdString;

        if (principal instanceof User) {
            userIdString = ((User) principal).getUserId().toString();
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            throw new IllegalArgumentException("Authentication 객체에 예기치 않은 Principal 타입: " + principal.getClass());
        } else {
            throw new IllegalArgumentException("Principal 타입에서 사용자 ID를 추출할 수 없습니다: " + principal.getClass());
        }

        Date now = new Date();
        long accessTokenExpirationMs = getAccessTokenExpirationTime() * 1000L;
        Date expiration = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }
    

    public String generateAccessToken(UUID userId) {
        String userIdString = userId.toString();
        Date now = new Date();
        long accessTokenExpirationMs = getAccessTokenExpirationTime() * 1000L;
        Date expiration = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userIdString;

        if (principal instanceof User) {
            userIdString = ((User) principal).getUserId().toString();
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
             userIdString = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            throw new IllegalArgumentException("Principal 타입에서 사용자 ID를 추출할 수 없습니다: " + principal.getClass());
        }

        Date now = new Date();
        long refreshTokenExpirationMs = getRefreshTokenExpirationTime();
        Date expiration = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }
    
    public String generateRefreshToken(UUID userId) {
        String userIdString = userId.toString();
        Date now = new Date();
        long refreshTokenExpirationMs = getRefreshTokenExpirationTime();
        Date expiration = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    public long getRefreshTokenExpirationTime() {
        String expirationTime = env.getProperty("JWT.token.refresh-expire-time");
        if (expirationTime == null) {
            logger.error("Refresh Token 만료 시간(JWT.token.refresh-expire-time) 설정이 없습니다. 기본값(7일)을 사용합니다.");
            return 7 * 24 * 60 * 60 * 1000L;
        }
        try {
            return Long.parseLong(expirationTime);
        } catch (NumberFormatException e) {
            logger.error("Refresh Token 만료 시간(JWT.token.refresh-expire-time) 형식이 잘못되었습니다: {}. 기본값(7일)을 사용합니다.", expirationTime, e);
            return 7 * 24 * 60 * 60 * 1000L;
        }
    }

    public long getAccessTokenExpirationTime() {
        String expirationTimeMs = env.getProperty("JWT.token.access-expire-time");
        long expirationMs;
        if (expirationTimeMs == null) {
            logger.error("Access Token 만료 시간(JWT.token.access-expire-time) 설정이 없습니다. 기본값(1시간)을 사용합니다.");
            expirationMs = 60 * 60 * 1000L;
        } else {
            try {
                expirationMs = Long.parseLong(expirationTimeMs);
            } catch (NumberFormatException e) {
                logger.error("Access Token 만료 시간(JWT.token.access-expire-time) 형식이 잘못되었습니다: {}. 기본값(1시간)을 사용합니다.", expirationTimeMs, e);
                expirationMs = 60 * 60 * 1000L;
            }
        }
        return expirationMs / 1000L;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("잘못된 JWT 서명입니다.");
        } catch (MalformedJwtException ex) {
            logger.error("잘못된 JWT 토큰 형식입니다.");
        } catch (ExpiredJwtException ex) {
            logger.warn("만료된 JWT 토큰입니다 (validateRefreshToken에서는 오류 아님): {}", ex.getMessage());
            return true;
        } catch (UnsupportedJwtException ex) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT 클레임 문자열이 비어 있거나 부적절합니다.");
        }
        return false;
    }

    public UUID getUserIdFromRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());
    }
    
    public SecretKey getSignKey()  {
        String secret = env.getProperty("JWT.secret-key");
        if (secret == null) {
            logger.error("JWT secret key (JWT.secret-key) is not configured!");
            throw new IllegalStateException("JWT secret key is not configured.");
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

   
    public JwtTokenProvider(final Environment env) {
        this.env = env;
    }
}
