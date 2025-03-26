package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class PromotionDetailImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionDetailId;

    private String imageUrl;

    private String alt;

    private Integer sortOrder;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionId;
}
