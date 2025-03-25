package com.starbucks.starvive.user.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    
    @Builder
    public UserCreateRequest(String email, String password, String name, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
} 