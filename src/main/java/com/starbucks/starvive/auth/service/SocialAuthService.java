package com.starbucks.starvive.auth.service;

import com.starbucks.starvive.auth.dto.SocialLoginDto;
import com.starbucks.starvive.auth.dto.TokenResponseDto;

public interface SocialAuthService {
    TokenResponseDto socialLogin(SocialLoginDto socialLoginDto);
} 