package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class LIstPromotionProductVo {

    private UUID productId;

    private String name;

    private String imageThumbUrl;

    private String imageThumbAlt;

    private int price;

    private int baseDiscountRate;

    @Builder
    public LIstPromotionProductVo(UUID productId, String name,
                                  String imageThumbUrl, String imageThumbAlt,
                                  int price, int baseDiscountRate) {
        this.productId = productId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.price = price;
        this.baseDiscountRate = baseDiscountRate;
    }
}
