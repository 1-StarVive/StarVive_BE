package com.starbucks.starvive.user.vo;

import com.starbucks.starvive.user.domain.Gender;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class UserVo {
    private String name;
    private String nickname;
    private LocalDateTime birth;
    private Gender gender;
} 