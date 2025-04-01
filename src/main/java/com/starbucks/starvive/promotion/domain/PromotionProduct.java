package com.starbucks.starvive.promotion.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionProduct extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
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
