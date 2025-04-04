package com.starbucks.starvive.user.dto.out;

import com.starbucks.starvive.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    
    public static SignInResponseDto from(User user, String accessToken, String refreshToken, long expiresIn) {
        return SignInResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(expiresIn)
            .build();
    }
}
