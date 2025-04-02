package com.starbucks.starvive.user.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestVo {
    private String email;
    private String password;

    @Builder
    public SignInRequestVo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
