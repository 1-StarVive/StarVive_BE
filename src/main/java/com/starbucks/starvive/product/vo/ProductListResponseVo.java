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
    private UUID productOptionId;
    private String imageThumbUrl;
    private String name;
    private String optionName;
    private int price;

    @Builder
    public ProductListResponseVo(UUID productId, UUID productOptionId, String imageThumbUrl, String name, String optionName, int price) {
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.imageThumbUrl = imageThumbUrl;
        this.name = name;
        this.optionName = optionName;
        this.price = price;
    }

    public static ProductListResponseVo from(ProductListResponseDto productListResponseDto) {
        return ProductListResponseVo.builder()
                .productId(productListResponseDto.getProductId())
                .productOptionId(productListResponseDto.getProductOptionId())
                .imageThumbUrl(productListResponseDto.getImageThumbUrl())
                .name(productListResponseDto.getName())
                .optionName(productListResponseDto.getOptionName())
                .price(productListResponseDto.getPrice())
                .build();
    }
}