package com.starbucks.starvive.user.dto.in;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.vo.SignUpRequestVo;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    
    private String loginId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private LocalDate birth;
    private Gender gender;

    private boolean termsAgreed;
    private boolean privacyAgreed;
    private boolean cardTermsAgreed;
    private boolean marketingEmailAgreed;
    private boolean marketingSmsAgreed;
    private boolean nicknameTermAgreed;

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
            .termsAgreed(vo.isTermsAgreed())
            .privacyAgreed(vo.isPrivacyAgreed())
            .cardTermsAgreed(vo.isCardTermsAgreed())
            .marketingEmailAgreed(vo.isMarketingEmailAgreed())
            .marketingSmsAgreed(vo.isMarketingSmsAgreed())
            .nicknameTermAgreed(vo.isNicknameTermAgreed())
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
            .termsAgreed(termsAgreed)
            .privacyAgreed(privacyAgreed)
            .cardTermsAgreed(cardTermsAgreed)
            .marketingEmailAgreed(marketingEmailAgreed)
            .marketingSmsAgreed(marketingSmsAgreed)
            .nicknameTermAgreed(nicknameTermAgreed)
            .build();
    }

    @Builder
    public SignUpRequestDto(String loginId, String email, String password, String name, String nickname, String phoneNumber, LocalDate birth, Gender gender, boolean termsAgreed, boolean privacyAgreed, boolean cardTermsAgreed, boolean marketingEmailAgreed, boolean marketingSmsAgreed, boolean nicknameTermAgreed) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.termsAgreed = termsAgreed;
        this.privacyAgreed = privacyAgreed;
        this.cardTermsAgreed = cardTermsAgreed;
        this.marketingEmailAgreed = marketingEmailAgreed;
        this.marketingSmsAgreed = marketingSmsAgreed;
        this.nicknameTermAgreed = nicknameTermAgreed;
    }
}
