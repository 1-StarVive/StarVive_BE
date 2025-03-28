package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class PromotionDetailImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionDetailId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String promotionDetailContent;

    private Integer order;

    private String promotionId;

    @Builder
    public PromotionDetailImage(UUID promotionDetailId, String promotionDetailContent,
                                Integer order, String promotionId) {
        this.promotionDetailId = promotionDetailId;
        this.promotionDetailContent = promotionDetailContent;
        this.order = order;
        this.promotionId = promotionId;
    }
}
