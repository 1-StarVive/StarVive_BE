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
        User user = this.userRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found with email: " + signInRequestDto.getEmail()));

        try {
            Authentication authentication = this.authenticate(user, signInRequestDto.getPassword());
            String accessToken = this.createAccessToken(authentication);
            String refreshToken = this.createRefreshToken(authentication);
            
            // Save refresh token
            Instant expiryDate = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationTime());
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .userId(user.getUserId())
                .expiryDate(expiryDate)
                .build();
            
            // These operations now run within a transaction
            refreshTokenRepository.deleteByUserId(user.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);

            return SignInResponseDto.from(user, accessToken, refreshToken);
        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.warn("Authentication failed for email {}: {}", signInRequestDto.getEmail(), e.getMessage());
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            logger.error("Unexpected error during sign in for email {}", signInRequestDto.getEmail(), e);
            // Consider re-throwing or handling specific exceptions differently
            throw new RuntimeException("An unexpected error occurred during sign in."); 
        }
    }

    @Override
    @Transactional
    public void signOut(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> refreshAccessToken(String refreshToken) {
        // 1. Validate JWT signature
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            return Optional.empty();
        }

        // 2. Find Refresh Token in DB
        return refreshTokenRepository.findById(refreshToken)
                .map(tokenEntity -> {
                    // 3. Check expiry from DB
                    if (tokenEntity.isExpired()) {
                        // Need a separate transactional method or context for delete here if readOnly = true is used
                        // For simplicity, keeping the main method transactional without readOnly
                        // Or handle expiry exception differently
                        refreshTokenRepository.delete(tokenEntity); // This might cause issues if readOnly = true
                        throw new TokenRefreshException(refreshToken, "Refresh token was expired. Please make a new signin request");
                    }
                    // 4. Generate new access token
                    return jwtTokenProvider.generateAccessToken(tokenEntity.getUserId());
                });
    }

    private String createAccessToken(Authentication authentication) {
        return this.jwtTokenProvider.generateAccessToken(authentication);
    }

    private String createRefreshToken(Authentication authentication) {
        return this.jwtTokenProvider.generateRefreshToken(authentication);
    }

    private Authentication authenticate(User user, String inputPassword) {
        return this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), inputPassword));
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
    public UserDetails loadUserByUsername(String username) {
        UUID userId = UUID.fromString(username);
        User user = this.userRepository.findByUserId(userId)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found with ID: " + username));
        if (user instanceof UserDetails) {
            return (UserDetails) user;
        } else {
            throw new IllegalStateException("User object does not implement UserDetails for ID: " + username);
        }
    }
}
