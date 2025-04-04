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


    public static Capacity fromString(String value) {
        for (Capacity capacity : Capacity.values()) {
            if (capacity.label.equals(value)) {
                return capacity;
            }
        }
        throw new IllegalArgumentException("Unknown capacity: " + value);
    }

    // 용량 ml 분류
    public static Capacity fromMilliliter(int ml) {
        if (ml >= 237 && ml <= 350) return SHORT;
        if (ml >= 355 && ml <= 444) return TALL;
        if (ml >= 473 && ml <= 503) return GRANDE;
        if (ml >= 591 && ml <= 710) return VENTI;
        if (ml >= 932 && ml <= 1185) return TRENTA;
        throw new IllegalArgumentException("해당 용량에 맞는 사이즈가 없습니다: " + ml + "ml");
    }
}
