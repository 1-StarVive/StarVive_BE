package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductResponseVo {

    private UUID productId;
    private String name;
    private String status;

    @Builder
    public ProductResponseVo(UUID productId, String name, String status) {
        this.productId = productId;
        this.name = name;
        this.status = status;
    }

    public static ProductResponseVo from(ProductResponseDto productResponseDto) {
        return ProductResponseVo.builder()
                .productId(productResponseDto.getProductId())
                .name(productResponseDto.getName())
                .status(productResponseDto.getProductStatus().name())
                .build();
    }
}
