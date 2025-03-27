package com.starbucks.starvive.auth.service;

import com.starbucks.starvive.auth.dto.SocialLoginDto;
import com.starbucks.starvive.auth.dto.TokenResponseDto;
import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.AuthType;
import com.starbucks.starvive.user.dto.in.UserRequestDto;
import com.starbucks.starvive.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialAuthServiceImpl implements SocialAuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public TokenResponseDto socialLogin(SocialLoginDto socialLoginDto) {
        // 소셜 계정으로 이미 가입된 사용자인지 확인
        User user = userRepository.findByEmail(socialLoginDto.getEmail())
                .orElseGet(() -> {
                    // 새로운 사용자 생성
                    UserRequestDto userRequestDto = UserRequestDto.builder()
                            .email(socialLoginDto.getEmail())
                            .name(socialLoginDto.getName())
                            .nickname(generateNickname(socialLoginDto.getName()))
                            .password(UUID.randomUUID().toString()) // 소셜 로그인은 비밀번호가 필요없으므로 랜덤 생성
                            .phoneNumber("010-0000-0000") // 소셜 로그인용 임시 전화번호
                            .build();
                    
                    userService.userCreate(userRequestDto);
                    return userRepository.findByEmail(socialLoginDto.getEmail())
                            .orElseThrow(() -> new RuntimeException("사용자 생성 실패"));
                });

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(Map.of("uuid", user.getUserId().toString()), 
            new org.springframework.security.core.userdetails.User(
                user.getUserId().toString(),
                "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            ));
        
        return TokenResponseDto.builder()
                .token(token)
                .build();
    }

    private String generateNickname(String name) {
        String baseNickname = name;
        String nickname = baseNickname;
        int counter = 1;
        
        // 닉네임이 중복되지 않을 때까지 반복
        while (userRepository.existsByNickname(nickname)) {
            nickname = baseNickname + "_" + counter;
            counter++;
        }
        
        return nickname;
    }
} 