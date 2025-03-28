package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionProduct {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionProductId;

    private String productId;

    private String promotionId;

    @Builder
    public PromotionProduct(UUID promotionProductId, String productId,
                            String promotionId) {
        this.promotionProductId = promotionProductId;
        this.productId = productId;
        this.promotionId = promotionId;
    }
}
