package com.starbucks.starvive.user.dto.out;

import com.starbucks.starvive.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserResponse {
    private UUID userId;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User.UserStatus status;
    
    @Builder
    public UserResponse(UUID userId, String email, String name, String nickname, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt, User.UserStatus status) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
    
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail().getValue())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber().getValue())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .status(user.getStatus())
                .build();
    }
} 