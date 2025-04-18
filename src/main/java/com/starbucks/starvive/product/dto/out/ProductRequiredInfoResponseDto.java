package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.ProductRequiredInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequiredInfoResponseDto {

    private String type;
    private String value;

    @Builder
    public ProductRequiredInfoResponseDto(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public static ProductRequiredInfoResponseDto from(ProductRequiredInfo productRequiredInfo) {
        return ProductRequiredInfoResponseDto.builder()
                .type(productRequiredInfo.getType())
                .value(productRequiredInfo.getValue())
                .build();
    }
}
