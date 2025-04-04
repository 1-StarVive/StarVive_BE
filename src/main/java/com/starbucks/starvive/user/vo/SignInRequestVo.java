package com.starbucks.starvive.user.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequestVo {
    private String loginId;
    private String password;

    @Builder
    public SignInRequestVo(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
