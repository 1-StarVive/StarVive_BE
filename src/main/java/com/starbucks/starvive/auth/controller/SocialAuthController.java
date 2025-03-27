package com.starbucks.starvive.auth.controller;

import com.starbucks.starvive.auth.dto.SocialLoginDto;
import com.starbucks.starvive.auth.dto.TokenResponseDto;
import com.starbucks.starvive.auth.service.SocialAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SocialAuthController {

    private final SocialAuthService socialAuthService;

    @PostMapping("/social")
    public ResponseEntity<TokenResponseDto> socialLogin(@RequestBody SocialLoginDto socialLoginDto) {
        TokenResponseDto response = socialAuthService.socialLogin(socialLoginDto);
        return ResponseEntity.ok(response);
    }

    // 테스트용 엔드포인트
    @PostMapping("/test-login")
    public ResponseEntity<TokenResponseDto> testSocialLogin() {
        SocialLoginDto testDto = SocialLoginDto.builder()
                .provider("google")
                .token("test-token")
                .email("test@example.com")
                .name("테스트유저")
                .build();
        
        TokenResponseDto response = socialAuthService.socialLogin(testDto);
        return ResponseEntity.ok(response);
    }
} 