package com.starbucks.starvive.product.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Capacity {
    SHORT("숏"),
    TALL("톨"),
    GRANDE("그란데"),
    VENTI("벤티"),
    TRENTA("트렌타");

    private final String label;

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Capacity fromString(String value) {
        for (Capacity capacity : Capacity.values()) {
            if (capacity.label.equals(value)) {
                return capacity;
            }
        }
        throw new IllegalArgumentException("Unknown capacity: " + value);
    }
}
