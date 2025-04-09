package com.starbucks.starvive.user.vo;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.user.domain.UserStatus;

import lombok.Getter;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;

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

    // 약관 동의 관련 필드 추가
    @AssertTrue(message = "이용약관에 동의해야 합니다.")
    private boolean termsAgreed;
    @AssertTrue(message = "개인정보 수집 및 이용에 동의해야 합니다.")
    private boolean privacyAgreed;
    @AssertTrue(message = "스타벅스 카드 이용약관에 동의해야 합니다.")
    private boolean cardTermsAgreed;
    @AssertTrue(message = "닉네임 관련 약관에 동의해야 합니다.")
    private boolean nicknameTermAgreed;
    
    private boolean marketingEmailAgreed;
    private boolean marketingSmsAgreed;
    
}
