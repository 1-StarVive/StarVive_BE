package com.starbucks.starvive.user.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(nullable = true, unique = true, length = 320)
    private String loginId;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @Column(nullable = true, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(name = "social_id", nullable = true, length = 255)
    private String socialId;

    @Column(nullable = true, length = 20)
    private String nickname;

    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Column(nullable = true)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialLoginType socialLoginType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean termsAgreed; // 이용약관_동의

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean privacyAgreed; // 개인정보_수집_및_이용_동의

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean cardTermsAgreed; // 스타벅스_카드_이용약관

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean marketingEmailAgreed; // 마케팅_정보_수신_동의_이메일

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean marketingSmsAgreed; // 마케팅_정보_수신_동의_SMS

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean nicknameTermAgreed; // nicknameTerm: 닉네임 관련 약관 동의 여부

    @Builder
    private User(String loginId, String email, String password, String name,
                 String socialId, String nickname, String phoneNumber, LocalDate birth,
                 Gender gender, SocialLoginType socialLoginType, UserStatus status,
                 boolean termsAgreed, boolean privacyAgreed, boolean cardTermsAgreed,
                 boolean marketingEmailAgreed, boolean marketingSmsAgreed, boolean nicknameTermAgreed) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.socialId = socialId;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.socialLoginType = socialLoginType;
        this.status = status;
        this.termsAgreed = termsAgreed;
        this.privacyAgreed = privacyAgreed;
        this.cardTermsAgreed = cardTermsAgreed;
        this.marketingEmailAgreed = marketingEmailAgreed;
        this.marketingSmsAgreed = marketingSmsAgreed;
        this.nicknameTermAgreed = nicknameTermAgreed;
    }

    public void updateSocialInfo(SocialLoginType socialLoginType, String socialId) {
        this.socialLoginType = socialLoginType;
        this.socialId = socialId;
    }

    ////////////////////////////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}