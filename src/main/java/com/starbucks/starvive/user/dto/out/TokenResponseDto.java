package com.starbucks.starvive.user.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JWT 토큰 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String token;
} 