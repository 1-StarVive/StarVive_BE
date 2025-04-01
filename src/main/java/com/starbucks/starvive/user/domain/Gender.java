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

    private final String description;

    @JsonValue
    public String getGender() {
        return description;
    } // JSON 직렬화

    @JsonCreator
    public static Gender fromString(String value) { // JSON 문자열을 Gender enum 역직렬화
        // 먼저 열거형 이름으로 시도
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(value)) {
                return gender;
            }
        }
        
        // 그 다음 설명 값으로 시도
        for (Gender gender : Gender.values()) {
            if (gender.description.equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}


