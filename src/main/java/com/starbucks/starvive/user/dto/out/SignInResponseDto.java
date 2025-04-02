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
    private String userId;
    private String email;
    
    public static SignInResponseDto from(User user, String accessToken, String refreshToken) {
        return SignInResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userId(user.getUserId().toString())
            .email(user.getEmail())
            .build();
    }
}
