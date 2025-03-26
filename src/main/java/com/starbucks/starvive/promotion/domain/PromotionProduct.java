package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class PromotionProduct {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionProductId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionId;

    @Enumerated(EnumType.STRING)
    private PromotionProductStatus promotionProductStatus;
}
