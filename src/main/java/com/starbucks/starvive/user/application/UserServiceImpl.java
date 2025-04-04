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
import com.starbucks.starvive.common.util.NicknameGenerator;
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

        User user = signUpRequestDto.toEntity();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        // 사용자가 입력한 닉네임 가져오기
        String requestedNickname = user.getNickname();
        // 닉네임이 없거나 비어있으면 랜덤 닉네임 생성
        String finalNickname = (requestedNickname == null || requestedNickname.isBlank())?
         NicknameGenerator.generateRandomNickname(): requestedNickname;

        User userToSave = User.builder()
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .password(encodedPassword)
            .name(user.getName().isBlank()? "사용자": user.getName())
            .nickname(finalNickname) // 최종 결정된 닉네임 사용
            .phoneNumber(user.getPhoneNumber().isBlank()? " ": user.getPhoneNumber())
            .birth(user.getBirth())
            .gender(user.getGender() == null ? Gender.OTHER: user.getGender())
            .socialLoginType(user.getSocialLoginType() == null ? SocialLoginType.NONE : user.getSocialLoginType())
            .status(UserStatus.ACTIVE)
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
        // 1. JWT 서명 검증
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            logger.warn("리프레시토큰 인증실패  : {}", refreshToken);
            return Optional.empty();
        }

        // 2. DB에서 Refresh Token 찾기
        return refreshTokenRepository.findById(refreshToken)
                .map(tokenEntity -> {
                    // 3. DB에서 만료 여부 확인
                    if (tokenEntity.isExpired()) {
                        logger.info("리프레시토큰 만료, 삭제중: {}", refreshToken);
                        refreshTokenRepository.delete(tokenEntity); // 만료된 토큰 삭제
                        throw new TokenRefreshException(refreshToken, "리프레시토큰 만료, 새로운 로그인 요청");
                    }
                    // 4. 새로운 Access Token 생성
                    logger.info("새로운 엑세스토큰 발급중: {}", tokenEntity.getUserId());
                    return Optional.of(jwtTokenProvider.generateAccessToken(tokenEntity.getUserId()));
                })
                .orElseGet(() -> {
                    logger.warn("리프레시토큰 데이터베이스에 없음: {}", refreshToken);
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
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("유저 없음: " + loginId));
        if (user instanceof UserDetails) {
            return (UserDetails) user;
        } else {
            throw new IllegalStateException("유저 객체가 UserDetails를 구현하지 않음: " + loginId);
        }
    }
}
