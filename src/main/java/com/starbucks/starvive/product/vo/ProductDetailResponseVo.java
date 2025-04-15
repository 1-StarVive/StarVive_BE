package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductDetailResponseVo {

    private UUID productId;
    private String name;
    private String productStatus;

    @Builder
    public ProductDetailResponseVo(UUID productId, String name, String productStatus) {
        this.productId = productId;
        this.name = name;
        this.productStatus = productStatus;
    }

    public static ProductDetailResponseVo from(ProductDetailResponseDto productDetailResponseDto) {
        return ProductDetailResponseVo.builder()
                .productId(productDetailResponseDto.getProductId())
                .name(productDetailResponseDto.getName())
                .productStatus(productDetailResponseDto.getProductStatus().name())
                .build();
    }
}
