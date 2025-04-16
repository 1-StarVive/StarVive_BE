package com.starbucks.starvive.user.vo;

import com.starbucks.starvive.user.domain.Gender;
import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestVo {

    @NotBlank(message = "로그인 ID는 필수 입력 항목입니다.")
    @Size(min = 5, max = 13, message = "로그인 ID는 5자 이상 13자 이하여야 합니다.")
    private String loginId;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phoneNumber;

    @Past(message = "생년월일은 현재 날짜보다 이전이여야 합니다.")
    private LocalDate birth;
    
    private String nickname;
    private Gender gender;

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
