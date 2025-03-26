package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class Promotion {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionId;

    private String title;

    private String url;

    private String alt;

    private String description;

    private LocalDateTime promotionStartAt;

    private LocalDateTime promotionEndAt;

    @Enumerated(EnumType.STRING)
    private PromotionStatus promotionStatus;
}