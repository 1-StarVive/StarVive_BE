package com.starbucks.starvive.user.application;

import java.time.Instant;
import java.util.UUID;
import java.util.Optional;

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
import com.starbucks.starvive.common.utils.NicknameGenerator;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.common.exception.BaseException;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {

        validateSignUpRequest(signUpRequestDto);

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        String requestedNickname = signUpRequestDto.getNickname();
        String finalNickname = (requestedNickname == null || requestedNickname.isBlank())
                ? NicknameGenerator.generateRandomNickname() : requestedNickname;

        String rawPhoneNumber = signUpRequestDto.getPhoneNumber();
        String finalPhoneNumber = (rawPhoneNumber == null || rawPhoneNumber.isBlank()) ? " " : rawPhoneNumber;

        User userToSave = User.builder()
            .loginId(signUpRequestDto.getLoginId())
            .email(signUpRequestDto.getEmail())
            .password(encodedPassword)
            .name(signUpRequestDto.getName().isBlank() ? "사용자": signUpRequestDto.getName())
            .nickname(finalNickname)
            .phoneNumber(finalPhoneNumber)
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

    private void validateSignUpRequest(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BaseException(ALREADY_EXIST_EMAIL);
        }

        String loginId = signUpRequestDto.getLoginId();
        if (loginId != null && !loginId.isBlank() && userRepository.existsByLoginId(loginId)) {
            throw new BaseException(ALREADY_EXIST_LOGIN_ID);
        }
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
            throw new BaseException(FAILED_TO_LOGIN);
        } catch (Exception e) {
            throw new BaseException(INTERNAL_SERVER_ERROR);
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
            return Optional.empty();
        }

        return refreshTokenRepository.findById(refreshToken)
                .map(tokenEntity -> {
                    if (tokenEntity.isExpired()) {
                        refreshTokenRepository.delete(tokenEntity);
                        throw new BaseException(TOKEN_EXPIRED);
                    }
                    return Optional.of(jwtTokenProvider.generateAccessToken(tokenEntity.getUserId()));
                })
                .orElseGet(() -> {
                    return Optional.empty();
                });
    }

    private String createAccessToken(Authentication authentication) {
        return this.jwtTokenProvider.generateAccessToken(authentication);
    }

    private String createRefreshToken(Authentication authentication) {
        return this.jwtTokenProvider.generateRefreshToken(authentication);
    }

    

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) {
        User user = this.userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BaseException(NO_EXIST_USER));
        if (user instanceof UserDetails) {
            return (UserDetails) user;
        } else {
            throw new BaseException(USER_NOT_IMPLEMENT_USERDETAILS);
        }
    }
}
