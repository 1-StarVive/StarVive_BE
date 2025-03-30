package com.starbucks.starvive.promotion.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionStatus {
    READY("기획전 준비중"),
    ONGOING("진행중"),
    ENDED("종료됨");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PromotionStatus fromString(String value) {
        for (PromotionStatus status : PromotionStatus.values()) {
            if (status.label.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown PromotionStatus: " + value);
    }
}
