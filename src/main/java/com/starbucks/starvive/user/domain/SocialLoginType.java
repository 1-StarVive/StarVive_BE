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
    TOSS("토스");

    private final String socialType;

    @JsonValue
    public String getSocialType() { return socialType; }

    @JsonCreator
    public static SocialLoginType fromSocialType(String value) {
        for (SocialLoginType type : SocialLoginType.values()) {
            if (type.getSocialType().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
