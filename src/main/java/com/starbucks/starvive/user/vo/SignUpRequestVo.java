package com.starbucks.starvive.user.vo;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.user.domain.UserStatus;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestVo {
    private String loginId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDate birth;
    private Gender gender;
    private SocialLoginType socialLoginType;
    private UserStatus status;
}
