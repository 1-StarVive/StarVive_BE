package com.starbucks.starvive.user.dto.out;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 사용자 응답 DTO
 */
@Getter
@Builder
public class UserResponseDto {
    private UUID userId;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDateTime birth;
    private Gender gender;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .gender(user.getGender())
                .status(user.getStatus() != null ? UserStatus.valueOf(user.getStatus()) : null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
} 