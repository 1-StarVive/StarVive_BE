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
    private String imageThumbUrl;
    private String name;
    private int price;

    @Builder
    public ProductListResponseVo(UUID productId,String imageThumbUrl, String name,int price) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.name = name;
        this.price = price;
    }

    public static ProductListResponseVo from(ProductListResponseDto productListResponseDto) {
        return ProductListResponseVo.builder()
                .productId(productListResponseDto.getProductId())
                .imageThumbUrl(productListResponseDto.getImageThumbUrl())
                .name(productListResponseDto.getName())
                .price(productListResponseDto.getPrice())
                .build();
    }
}