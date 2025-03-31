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

    private final String status;

    @JsonValue
    public String getStatus() { return status; }

    @JsonCreator
    public static UserStatus fromString(String value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.status.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
