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
import com.starbucks.starvive.user.domain.UserStatus;
import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.RefreshToken;
import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import com.starbucks.starvive.user.repository.UserRepository;
import com.starbucks.starvive.user.repository.RefreshTokenRepository;
import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.common.exception.TokenRefreshException;
import com.starbucks.starvive.common.utils.NicknameGenerator;
import com.starbucks.starvive.user.domain.SocialLoginType;

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

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        String requestedNickname = signUpRequestDto.getNickname();
        String finalNickname = (requestedNickname == null || requestedNickname.isBlank())
                ? NicknameGenerator.generateRandomNickname() : requestedNickname;

        User userToSave = User.builder()
            .loginId(signUpRequestDto.getLoginId()) 
            .email(signUpRequestDto.getEmail()) 
            .password(encodedPassword) 
            .name(signUpRequestDto.getName().isBlank()? "사용자": signUpRequestDto.getName()) 
            .nickname(finalNickname) 
            .phoneNumber(signUpRequestDto.getPhoneNumber().isBlank()? " ": signUpRequestDto.getPhoneNumber()) 
            .birth(signUpRequestDto.getBirth()) 
            .gender(signUpRequestDto.getGender() == null ? Gender.OTHER: signUpRequestDto.getGender()) 
            .socialLoginType(SocialLoginType.NONE) 
            .status(UserStatus.ACTIVE) 
            .termsAgreed(signUpRequestDto.isTermsAgreed())
            .privacyAgreed(signUpRequestDto.isPrivacyAgreed())
            .cardTermsAgreed(signUpRequestDto.isCardTermsAgreed())
            .marketingEmailAgreed(signUpRequestDto.isMarketingEmailAgreed())
            .marketingSmsAgreed(signUpRequestDto.isMarketingSmsAgreed())
            .nicknameTermAgreed(signUpRequestDto.isNicknameTermAgreed())
            .build();

        this.userRepository.save(userToSave);
    }

    @Override
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    signInRequestDto.getLoginId(),
                    signInRequestDto.getPassword()
                )
            );

            User authenticatedUser = (User) authentication.getPrincipal();

            String accessToken = this.createAccessToken(authentication);
            String refreshToken = this.createRefreshToken(authentication);

            long expiresIn = jwtTokenProvider.getAccessTokenExpirationTime();

            Instant expiryDate = Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpirationTime());
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .userId(authenticatedUser.getUserId())
                .expiryDate(expiryDate)
                .build();

            refreshTokenRepository.deleteByUserId(authenticatedUser.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);

            return SignInResponseDto.from(authenticatedUser, accessToken, refreshToken, expiresIn);

        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.warn("로그인 실패  : {}", signInRequestDto.getLoginId());
            throw new org.springframework.security.authentication.BadCredentialsException("로그인 실패");
        } catch (Exception e) {
            logger.error("로그인 중 예상치 못한 오류  : {}", signInRequestDto.getLoginId(), e);
            throw new RuntimeException("로그인 중 예상치 못한 오류 발생");
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
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            logger.warn("리프레시토큰 인증실패  : {}", refreshToken);
            return Optional.empty();
        }

        return refreshTokenRepository.findById(refreshToken)
                .map(tokenEntity -> {
                    if (tokenEntity.isExpired()) {
                        logger.info("리프레시토큰 만료, 삭제중: {}", refreshToken);
                        refreshTokenRepository.delete(tokenEntity);
                        throw new TokenRefreshException(refreshToken, "리프레시토큰 만료, 새로운 로그인 요청");
                    }
                    logger.info("새로운 엑세스토큰 발급중: {}", tokenEntity.getUserId());
                    return Optional.of(jwtTokenProvider.generateAccessToken(tokenEntity.getUserId()));
                })
                .orElseGet(() -> {
                    logger.warn("리프레시토큰 데이터베이스에 없음: {}", refreshToken);
                    return Optional.empty();
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
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("유저 없음: " + loginId));
        if (user instanceof UserDetails) {
            return (UserDetails) user;
        } else {
            throw new IllegalStateException("유저 객체가 UserDetails를 구현하지 않음: " + loginId);
        }
    }
}
