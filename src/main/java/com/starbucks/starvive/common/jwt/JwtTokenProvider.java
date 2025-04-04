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
                .setSigningKey(getSignKey()) // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 서명 검증 (실패 시 예외 발생)
                .getBody(); // 검증 성공 시 Payload(Claims) 반환
    }

    public String generateAccessToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userIdString;

        if (principal instanceof User) {
            userIdString = ((User) principal).getUserId().toString();
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // 이 경우는 UserDetailsService가 User 객체 대신 기본 UserDetails를 반환할 때 발생 가능하나,
            // 현재 구현에서는 User 객체를 반환하므로 이 분기는 사실상 예외적인 상황을 대비합니다.
            throw new IllegalArgumentException("Authentication 객체에 예기치 않은 Principal 타입: " + principal.getClass());
        } else {
            // OAuth2User 등 다른 타입의 Principal이 올 경우에 대한 처리 (현재 로직에서는 User 기대)
            throw new IllegalArgumentException("Principal 타입에서 사용자 ID를 추출할 수 없습니다: " + principal.getClass());
        }

        Date now = new Date(); // 현재 시간
        // 설정에서 Access Token 만료 시간(ms) 가져오기
        long accessTokenExpirationMs = getAccessTokenExpirationTime() * 1000L; // getAccessTokenExpirationTime()은 초 단위 반환 가정
        Date expiration = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString) // 토큰의 주체(Subject)로 사용자 ID 설정
                .setIssuedAt(now) // 토큰 발급 시간 설정
                .setExpiration(expiration) // 토큰 만료 시간설정
                .signWith(getSignKey()) // 서명 키와 알고리즘으로 서명
                .compact(); // 직렬화된 토큰 문자열 생성
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
        long refreshTokenExpirationMs = getRefreshTokenExpirationTime(); // Refresh Token 만료 시간(ms) 가져오기
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
        long refreshTokenExpirationMs = getRefreshTokenExpirationTime(); // Refresh Token 만료 시간(ms) 가져오기
        Date expiration = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    public long getRefreshTokenExpirationTime() {
        // 환경 변수 또는 application.yml에서 "JWT.token.refresh-expire-time" 값 가져오기
        String expirationTime = env.getProperty("JWT.token.refresh-expire-time");
        if (expirationTime == null) {
            logger.error("Refresh Token 만료 시간(JWT.token.refresh-expire-time) 설정이 없습니다. 기본값(7일)을 사용합니다.");
            // 기본값: 7일 (밀리초 단위)
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
        // 환경 변수 또는 application.yml에서 "JWT.token.access-expire-time" 값 가져오기 (밀리초 단위로 가정)
        String expirationTimeMs = env.getProperty("JWT.token.access-expire-time");
        long expirationMs;
        if (expirationTimeMs == null) {
            logger.error("Access Token 만료 시간(JWT.token.access-expire-time) 설정이 없습니다. 기본값(1시간)을 사용합니다.");
            // 기본값: 1시간 (밀리초 단위)
            expirationMs = 60 * 60 * 1000L;
        } else {
            try {
                expirationMs = Long.parseLong(expirationTimeMs);
            } catch (NumberFormatException e) {
                logger.error("Access Token 만료 시간(JWT.token.access-expire-time) 형식이 잘못되었습니다: {}. 기본값(1시간)을 사용합니다.", expirationTimeMs, e);
                // 기본값: 1시간 (밀리초 단위)
                expirationMs = 60 * 60 * 1000L;
            }
        }
        // 밀리초를 초로 변환하여 반환
        return expirationMs / 1000L;
    }

    public boolean validateRefreshToken(String token) {
        try {
            // 토큰 파싱 및 서명 검증 시도
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true; // 검증 성공
        } catch (SignatureException ex) {
            logger.error("잘못된 JWT 서명입니다.");
        } catch (MalformedJwtException ex) {
            logger.error("잘못된 JWT 토큰 형식입니다.");
        } catch (ExpiredJwtException ex) {
            logger.warn("만료된 JWT 토큰입니다 (validateRefreshToken에서는 오류 아님): {}", ex.getMessage());
            return true; // 서명 자체는 유효했을 수 있으므로 일단 true 반환 (필요시 false로 변경 가능)
        } catch (UnsupportedJwtException ex) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            // 토큰 문자열이 null이거나 비어있는 경우 등
            logger.error("JWT 클레임 문자열이 비어 있거나 부적절합니다.");
        }
        return false; // 검증 실패
    }

    public UUID getUserIdFromRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());
    }
    
    public SecretKey getSignKey()  {
        // 설정에서 비밀 키 문자열 가져오기
        String secret = env.getProperty("JWT.secret-key");
        if (secret == null) {
            logger.error("JWT secret key (JWT.secret-key) is not configured!");
            // 적절한 예외 처리 또는 기본 키 사용 (보안상 매우 위험)
            throw new IllegalStateException("JWT secret key is not configured.");
        }
        // 비밀 키 문자열을 byte 배열로 변환하여 HMAC-SHA 키 생성
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

   
    public JwtTokenProvider(final Environment env) {
        this.env = env;
    }
}
