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
import java.security.Key;
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

    /**
     * 토큰의 유효성을 검증하고 토큰에서 사용자 UUID(Subject)를 추출합니다.
     * @param token 검증할 JWT 토큰
     * @return 사용자 UUID 문자열
     * @throws IllegalArgumentException 토큰에 유저 정보(Subject)가 없는 경우
     */
    public String validateAndGetUserUuid(String token) throws IllegalArgumentException {
        try {
            return (String)this.extractClaim(token, Claims::getSubject);
        } catch (NullPointerException var3) {
            throw new IllegalArgumentException("토큰에 담긴 유저 정보가 없습니다");
        }
    }
    
    /**
     * 토큰에서 특정 클레임(Claim)을 추출합니다.
     * @param token JWT 토큰
     * @param claimsResolver 클레임에서 원하는 정보를 추출하는 함수
     * @return 추출된 클레임 정보
     * @param <T> 추출할 정보의 타입
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return (T)claimsResolver.apply(claims);
    }

    /**
     * 토큰에서 모든 클레임(Claims)을 추출합니다.
     * 서명 검증을 포함합니다.
     * @param token JWT 토큰
     * @return 토큰의 Claims 객체
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Authentication 객체를 기반으로 Access Token을 생성합니다.
     * Principal이 User 타입인 경우 userId를 사용합니다.
     * @param authentication 인증 정보
     * @return 생성된 Access Token 문자열
     */
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
        Date expiration = new Date(now.getTime() + 
            Long.parseLong(env.getProperty("JWT.token.access-expire-time")));
        
        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }
    
    // Overload generateAccessToken to accept UUID directly
    public String generateAccessToken(UUID userId) {
        String userIdString = userId.toString();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 
            Long.parseLong(env.getProperty("JWT.token.access-expire-time")));
        
        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    /**
     * Authentication 객체를 기반으로 Refresh Token을 생성합니다.
     * Principal 타입에 따라 userId를 추출합니다.
     * @param authentication 인증 정보
     * @return 생성된 Refresh Token 문자열
     */
    public String generateRefreshToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userIdString;

        if (principal instanceof User) {
            userIdString = ((User) principal).getUserId().toString();
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
             // DB 인증의 UserDetails인 경우 getUsername()이 UUID 문자열을 반환한다고 가정
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
    
    // Add overloaded method to accept UUID directly
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

    /**
     * Refresh Token의 만료 시간(밀리초)을 설정에서 가져옵니다.
     * @return Refresh Token 만료 시간 (ms)
     */
    public long getRefreshTokenExpirationTime() {
        // 환경 변수 또는 application.yml에서 값 가져오기
        String expirationTime = env.getProperty("JWT.token.refresh-expire-time");
        if (expirationTime == null) {
            logger.error("Refresh token expiration time (JWT.token.refresh-expire-time) is not configured.");
            // 기본값 또는 적절한 예외 처리
            return 7 * 24 * 60 * 60 * 1000L; // 예: 7일
        }
        return Long.parseLong(expirationTime);
    }

    /**
     * Access Token의 만료 시간(밀리초)을 설정에서 가져옵니다.
     * @return Access Token 만료 시간 (ms)
     */
    public long getAccessTokenExpirationTime() {
        // 환경 변수 또는 application.yml에서 값 가져오기
        String expirationTime = env.getProperty("JWT.token.access-expire-time");
        if (expirationTime == null) {
            logger.error("Access token expiration time (JWT.token.access-expire-time) is not configured.");
            // 기본값 또는 적절한 예외 처리
            return 60 * 60 * 1000L; // 예: 1시간
        }
        return Long.parseLong(expirationTime);
    }

    /**
     * Refresh Token의 유효성을 검증합니다 (서명 확인).
     * 만료 여부 검증은 DB에서 별도로 수행해야 합니다.
     * @param token 검증할 Refresh Token
     * @return 유효하면 true, 아니면 false
     */
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("잘못된 JWT 서명입니다.");
        } catch (MalformedJwtException ex) {
            logger.error("잘못된 JWT 토큰입니다.");
        } catch (ExpiredJwtException ex) {
            logger.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException ex) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT 클레임 문자열이 비어 있습니다.");
        }
        return false;
    }

    /**
     * Refresh Token에서 사용자 ID(UUID)를 추출합니다.
     * @param token Refresh Token
     * @return 사용자 ID (UUID)
     */
    public UUID getUserIdFromRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    /**
     * 토큰 서명에 사용할 SecretKey를 생성합니다.
     * 설정 파일(application.yml) 또는 환경 변수에서 JWT 비밀 키를 가져옵니다.
     * @return SecretKey 객체
     */
    public SecretKey getSignKey()  {
        return Keys.hmacShaKeyFor(this.env.getProperty("JWT.secret-key").getBytes());
    }

    public JwtTokenProvider(final Environment env) {
        this.env = env;
    }
}
