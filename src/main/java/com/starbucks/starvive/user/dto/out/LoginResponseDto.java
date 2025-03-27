package com.starbucks.starvive.user.dto.out;

import com.starbucks.starvive.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String token;
    private UserResponseDto user;

    public static LoginResponseDto from(String token, User user) {
        return LoginResponseDto.builder()
                .token(token)
                .user(UserResponseDto.fromEntity(user))
                .build();
    }
} 