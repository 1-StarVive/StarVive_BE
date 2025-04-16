package com.starbucks.starvive.user.dto.out;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    
    private String loginId;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private UserStatus status;
    
    @Builder
    public UserResponse(String loginId, String email, String name, String nickname, String phoneNumber, UserStatus status) {
        this.loginId = loginId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
    
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .build();
    }
} 