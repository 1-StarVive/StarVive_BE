package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class WishProductResponseDto {

    private UUID productId;
    private String thumbnailUrl;
    private String thumbnailAlt;
    private String name;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private String code;
    private boolean limitedEdition; // 한정판 여부
    private boolean topProduct;     // 베스트 상픔 여부
    private boolean newProduct;     // 신상품 여부

    public static WishProductResponseDto from(Product product) {
        return WishProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .thumbnailUrl(product.getThumbnailUrl())
                .alt(product.getThumbnailAlt())
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .discountedPrice(product.getDiscountedPrice())
                .code(product.getCode())
                .isLimited(product.Limited())
                .isBest(product.Best())
                .build();
    }
}
