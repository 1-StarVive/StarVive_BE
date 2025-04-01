package com.starbucks.starvive.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductListVO {

    private UUID productId;

    private String productName;

    private String mainImageUrl;

    private String mainImageAlt;

    private int productPrice;

    private int productBaseDiscountRate;

    private String productIdStr;

    private LocalDateTime createdAt;

    private Boolean wasNew;

    @Builder
    public ProductListVO(UUID productId, String productName,
                         String mainImageUrl, String mainImageAlt,
                         int productPrice, int productBaseDiscountRate,
                         String productIdStr, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.mainImageUrl = mainImageUrl;
        this.mainImageAlt = mainImageAlt;
        this.productPrice = productPrice;
        this.productBaseDiscountRate = productBaseDiscountRate;
        this.productIdStr = productIdStr;
        this.createdAt = createdAt;
        this.wasNew = createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }
}
