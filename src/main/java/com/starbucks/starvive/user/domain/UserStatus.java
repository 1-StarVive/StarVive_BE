package com.starbucks.starvive.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제됨");

    private final String description;

    @JsonValue
    public String getStatus() { return description; }

    @JsonCreator
    public static UserStatus fromString(String value) {
        // 먼저 열거형 이름으로 시도
        for (UserStatus status : UserStatus.values()) {
            if (status.name().equals(value)) {
                return status;
            }
        }
        
        // 그 다음 설명 값으로 시도
        for (UserStatus status : UserStatus.values()) {
            if (status.description.equals(value)) {
                return status;
            }
        }
        
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
