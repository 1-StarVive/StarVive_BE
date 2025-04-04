package com.starbucks.starvive.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialLoginType {
    GOOGLE("구글"),
    KAKAO("카카오"),
    NONE("미연동");

    private final String description;

    @JsonValue
    public String getSocialType() { return description; }

    @JsonCreator
    public static SocialLoginType fromSocialType(String value) {
        // 먼저 열거형 이름으로 시도
        for (SocialLoginType type : SocialLoginType.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        
        // 그 다음 설명 값으로 시도
        for (SocialLoginType type : SocialLoginType.values()) {
            if (type.description.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
