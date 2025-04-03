package com.starbucks.starvive.user.application;

import java.time.Instant;
import java.util.UUID;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.RefreshToken;
import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import com.starbucks.starvive.user.repository.UserRepository;
import com.starbucks.starvive.user.repository.RefreshTokenRepository;
import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.common.exception.TokenRefreshException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        User user = signUpRequestDto.toEntity();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User userToSave = User.builder()
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .password(encodedPassword)
            .name(user.getName())
            .nickname(user.getNickname())
            .phoneNumber(user.getPhoneNumber())
            .birth(user.getBirth())
            .gender(user.getGender())
            .socialLoginType(user.getSocialLoginType())
            .status(user.getStatus())
            .build();

        this.userRepository.save(userToSave);
    }

    @Override
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        try {
            // 1. AuthenticationManager를 사용하여 인증 수행
            Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    signInRequestDto.getLoginId(),
                    signInRequestDto.getPassword()
                )
            );

            // 2. Principal에서 인증된 User 객체 가져오기
            User authenticatedUser = (User) authentication.getPrincipal();

            // 3. Authentication 객체를 사용하여 토큰 생성
            String accessToken = this.createAccessToken(authentication);
            String refreshToken = this.createRefreshToken(authentication);

            // 4. Access Token 만료 시간 가져오기 (ms)
            long expiresIn = jwtTokenProvider.getAccessTokenExpirationTime();

            // 5. Refresh Token 저장
            Instant expiryDate = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationTime()); // Refresh 토큰 저장 시에는 Refresh 만료 시간 사용
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .userId(authenticatedUser.getUserId())
                .expiryDate(expiryDate)
                .build();

            refreshTokenRepository.deleteByUserId(authenticatedUser.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);

            // 6. 응답 DTO 반환 (expiresIn 포함)
            return SignInResponseDto.from(authenticatedUser, accessToken, refreshToken, expiresIn);

        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.warn("Authentication failed for loginId {}: {}", signInRequestDto.getLoginId(), e.getMessage());
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            logger.error("Unexpected error during sign in for loginId {}", signInRequestDto.getLoginId(), e);
            throw new RuntimeException("An unexpected error occurred during sign in.");
        }
    }

    @Override
    @Transactional
    public void signOut(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public Optional<String> refreshAccessToken(String refreshToken) {
        // 1. JWT 서명 검증
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            logger.warn("Refresh token validation failed: {}", refreshToken);
            return Optional.empty();
        }

        // 2. DB에서 Refresh Token 찾기
        return refreshTokenRepository.findById(refreshToken)
                .map(tokenEntity -> {
                    // 3. DB에서 만료 여부 확인
                    if (tokenEntity.isExpired()) {
                        logger.info("Refresh token expired, deleting: {}", refreshToken);
                        refreshTokenRepository.delete(tokenEntity); // 만료된 토큰 삭제
                        throw new TokenRefreshException(refreshToken, "Refresh token was expired. Please make a new signin request");
                    }
                    // 4. 새로운 Access Token 생성
                    logger.info("Issuing new access token for user: {}", tokenEntity.getUserId());
                    return Optional.of(jwtTokenProvider.generateAccessToken(tokenEntity.getUserId()));
                })
                .orElseGet(() -> {
                    logger.warn("Refresh token not found in DB: {}", refreshToken);
                    return Optional.empty(); // 검증 후 토큰을 찾을 수 없는 경우 처리 (예: 동시 삭제)
                });
    }

    private String createAccessToken(Authentication authentication) {
        return this.jwtTokenProvider.generateAccessToken(authentication);
    }

    private String createRefreshToken(Authentication authentication) {
        return this.jwtTokenProvider.generateRefreshToken(authentication);
    }

    public UserServiceImpl(final UserRepository userRepository, 
                          final PasswordEncoder passwordEncoder, 
                          final AuthenticationManager authenticationManager, 
                          final JwtTokenProvider jwtTokenProvider,
                          final RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) {
        User user = this.userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found with loginId: " + loginId));
        if (user instanceof UserDetails) {
            return (UserDetails) user;
        } else {
            throw new IllegalStateException("User object does not implement UserDetails for loginId: " + loginId);
        }
    }
}
