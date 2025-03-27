package com.starbucks.starvive.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginDto {
    private String provider;  // google, kakao 등
    private String token;     // 소셜 제공자에서 받은 액세스 토큰
    private String email;     // 소셜 제공자에서 받은 이메일
    private String name;      // 소셜 제공자에서 받은 이름
} 