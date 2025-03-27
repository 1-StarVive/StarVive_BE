package com.starbucks.starvive.user.dto.in;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.vo.UserVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class UserRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDateTime birth;
    private Gender gender;

    @Builder
    public UserRequestDto(String email, String password, String name, String nickname,
                         String phoneNumber, LocalDateTime birth, Gender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
    }
   
    public static UserRequestDto from(UserVo userVo, String email, String password) {
        return UserRequestDto.builder()
                .email(email)
                .password(password)
                .name(userVo.getName())
                .nickname(userVo.getNickname())
                .birth(userVo.getBirth())
                .gender(userVo.getGender())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .birth(this.birth)
                .gender(this.gender)
                .build();
    }
} 