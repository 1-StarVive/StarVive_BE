package com.starbucks.starvive.promotion.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
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
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID promotionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String notice;

    @Column(nullable = false)
    private LocalDate promotionStartAt;

    @Column(nullable = false)
    private LocalDate promotionEndAt;

    @Column(nullable = false)
    private Boolean mainExpose; // 메인 노출 여부

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String promotionDetailContent;

    @Enumerated(EnumType.STRING)
    private PromotionStatus promotionStatus;

    @Builder
    public Promotion(UUID promotionId, String title, String notice,
                     LocalDate promotionStartAt, LocalDate promotionEndAt,
                     Boolean mainExpose, String promotionDetailContent,
                     PromotionStatus promotionStatus) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.mainExpose = mainExpose;
        this.promotionDetailContent = promotionDetailContent;
        this.promotionStatus = promotionStatus;
    }
}