package com.starbucks.starvive.user.dto.in;

import com.starbucks.starvive.user.vo.LoginVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class LoginRequestDto {
    private String email;
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * VO를 DTO로 변환
     */
    public static LoginRequestDto from(LoginVo loginVo) {
        return LoginRequestDto.builder()
                .email(loginVo.getEmail())
                .password(loginVo.getPassword())
                .build();
    }
} 