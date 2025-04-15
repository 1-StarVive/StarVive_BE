package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.domain.ProductStatus;
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
    private ProductStatus productStatus;

    @Builder
    public ProductResponseVo(UUID productId, String name, ProductStatus productStatus) {
        this.productId = productId;
        this.name = name;
        this.productStatus = productStatus;
    }

    public static ProductResponseVo from(ProductResponseDto productResponseDto) {
        return ProductResponseVo.builder()
                .productId(productResponseDto.getProductId())
                .name(productResponseDto.getName())
                .productStatus(productResponseDto.getProductStatus())
                .build();
    }
}
