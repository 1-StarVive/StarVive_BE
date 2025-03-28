package com.starbucks.starvive.promotion.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionId;

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    @Enumerated(EnumType.STRING)
    private PromotionStatus promotionStatus;

    @Builder
    public Promotion(UUID promotionId, String title,
                     String notice,
                     LocalDate promotionStartAt, LocalDate promotionEndAt,
                     PromotionStatus promotionStatus) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.promotionStatus = promotionStatus;
    }
}