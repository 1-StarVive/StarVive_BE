package com.starbucks.starvive.user.dto.in;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import com.starbucks.starvive.user.vo.SignUpRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SignUpRequestDto {
    
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

    public static SignUpRequestDto from(SignUpRequestVo vo) {
        return SignUpRequestDto.builder()
            .loginId(vo.getLoginId())
            .email(vo.getEmail())
            .password(vo.getPassword())
            .name(vo.getName())
            .nickname(vo.getNickname())
            .phoneNumber(vo.getPhoneNumber())
            .birth(vo.getBirth())
            .gender(vo.getGender())
            .socialLoginType(vo.getSocialLoginType())
            .status(vo.getStatus())
            .build();
    }

    public User toEntity() {
        return User.builder()
            .loginId(loginId)
            .email(email)
            .password(password)
            .name(name)
            .nickname(nickname)
            .phoneNumber(phoneNumber)
            .birth(birth)
            .gender(gender)
            .socialLoginType(socialLoginType)
            .status(status)
            .build();
    }
}
