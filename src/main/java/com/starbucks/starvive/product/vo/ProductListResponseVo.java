package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductListResponseVo {

    private UUID productId;
    private String name;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private int baseDiscountRate;
    private int price;

    @Builder
    public ProductListResponseVo(UUID productId, String name, String imageThumbUrl, String imageThumbAlt, int baseDiscountRate, int price) {
        this.productId = productId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.baseDiscountRate = baseDiscountRate;
        this.price = price;
    }

    public static ProductListResponseVo from(ProductListResponseDto productListResponseDto) {
        return ProductListResponseVo.builder()
                .productId(productListResponseDto.getProductId())
                .name(productListResponseDto.getName())
                .imageThumbUrl(productListResponseDto.getImageThumbUrl())
                .imageThumbAlt(productListResponseDto.getImageThumbAlt())
                .baseDiscountRate(productListResponseDto.getBaseDiscountRate())
                .price(productListResponseDto.getPrice())
                .build();
    }
}