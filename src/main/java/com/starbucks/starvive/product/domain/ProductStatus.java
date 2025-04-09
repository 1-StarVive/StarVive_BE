package com.starbucks.starvive.product.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    READY("상품준비중"),
    ONGOING("판매중"),
    SOLD_OUT("품절");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ProductStatus fromString(String value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.label.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown ProductStatus: " + value);
    }
}

