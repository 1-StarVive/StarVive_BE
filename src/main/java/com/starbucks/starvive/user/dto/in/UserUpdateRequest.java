package com.starbucks.starvive.user.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private String nickname;
    private String phoneNumber;
    
    @Builder
    public UserUpdateRequest(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
} 