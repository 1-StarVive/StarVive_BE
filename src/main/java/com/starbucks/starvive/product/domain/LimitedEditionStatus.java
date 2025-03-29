package com.starbucks.starvive.product.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LimitedEditionStatus {
    READY("상품준비중"),
    ONGOING("진행중"),
    ENDED("종료");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static LimitedEditionStatus fromString(String value) {
        for (LimitedEditionStatus status : LimitedEditionStatus.values()) {
            if (status.label.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
