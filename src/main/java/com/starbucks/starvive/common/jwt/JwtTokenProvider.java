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

    public String validateAndGetUserUuid(String token) throws IllegalArgumentException {
        try {
            return (String)this.extractClaim(token, Claims::getSubject);
        } catch (NullPointerException var3) {
            throw new IllegalArgumentException("토큰에 담긴 유저 정보가 없습니다");
        }
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return (T)claimsResolver.apply(claims);
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
            throw new IllegalArgumentException("Unexpected principal type in Authentication: " + principal.getClass());
        } else {
            throw new IllegalArgumentException("Cannot extract user ID from principal type: " + principal.getClass());
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

    public String generateRefreshToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String userIdString;

        if (principal instanceof User) {
            userIdString = ((User) principal).getUserId().toString();
        } else {
            throw new IllegalArgumentException("Cannot extract user ID from principal type: " + principal.getClass());
        }

        Date now = new Date();
        long refreshTokenExpirationMs = Long.parseLong(env.getProperty("JWT.token.refresh-expire-time"));
        Date expiration = new Date(now.getTime() + refreshTokenExpirationMs);
        
        return Jwts.builder()
                .setSubject(userIdString)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    public long getRefreshTokenExpirationTime() {
        return Long.parseLong(env.getProperty("JWT.token.refresh-expire-time"));
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token"); // Should be handled by checking DB expiry
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public UUID getUserIdFromRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    public SecretKey getSignKey()  {
        return Keys.hmacShaKeyFor(this.env.getProperty("JWT.secret-key").getBytes());
    }

    public JwtTokenProvider(final Environment env) {
        this.env = env;
    }
}
