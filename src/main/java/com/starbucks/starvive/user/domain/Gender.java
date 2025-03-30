package com.starbucks.starvive.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    OTHER("기타");

    private final String gender;

    @JsonValue
    public String getGender() {
        return gender;
    } // JSON 직렬화

    @JsonCreator
    public static Gender fromString(String value) { // JSON 문자열을 Gender enum 역직렬화
        for (Gender gender: Gender.values()) {
            if (gender.gender.equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("UnKnown value: " + value);
    }
}


