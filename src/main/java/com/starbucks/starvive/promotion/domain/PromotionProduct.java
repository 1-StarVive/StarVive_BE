package com.starbucks.starvive.promotion.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PromotionProduct extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID promotionProductId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID promotionId;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted = false;

    @Builder
    public PromotionProduct(UUID promotionProductId, UUID productId,
                            UUID promotionId, boolean deleted) {
        this.promotionProductId = promotionProductId;
        this.productId = productId;
        this.promotionId = promotionId;
        this.deleted = deleted;
    }
}
