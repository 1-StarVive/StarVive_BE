package com.starbucks.starvive.user.domain;

import com.starbucks.starvive.user.vo.Email;
import com.starbucks.starvive.user.vo.Password;
import com.starbucks.starvive.user.vo.PhoneNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @Builder
    private User(Email email, Password password, String name, String nickname, PhoneNumber phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = UserStatus.ACTIVE;
    }

    // 회원 정보 업데이트
    public void updateProfile(String nickname, PhoneNumber phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }

    // 비밀번호 변경
    public void changePassword(Password newPassword) {
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    // 회원 탈퇴 처리
    public void delete() {
        this.status = UserStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 회원 상태 변경
    public void changeStatus(UserStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    // 사용자 상태를 표현하는 열거형
    public enum UserStatus {
        ACTIVE,     // 활성화 상태
        INACTIVE,   // 비활성화 상태
        SUSPENDED,  // 정지 상태
        DELETED     // 삭제 상태
    }
}
