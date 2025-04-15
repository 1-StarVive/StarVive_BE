package com.starbucks.starvive.promotion.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class Promotion extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID promotionId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
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

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted = false;

    @Builder
    public Promotion(UUID promotionId, String title, String notice,
                     LocalDate promotionStartAt, LocalDate promotionEndAt,
                     Boolean mainExpose, String promotionDetailContent,
                     PromotionStatus promotionStatus, boolean deleted) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.mainExpose = mainExpose;
        this.promotionDetailContent = promotionDetailContent;
        this.promotionStatus = promotionStatus;
        this.deleted = deleted;
    }

    public void update(UpdatePromotionRequestDto updatePromotionRequestDto
    ) {
        if (updatePromotionRequestDto.getTitle() != null) this.title = updatePromotionRequestDto.getTitle();
        if (updatePromotionRequestDto.getNotice() != null) this.notice = updatePromotionRequestDto.getNotice();
        if (updatePromotionRequestDto.getPromotionStartAt() != null) this.promotionStartAt = updatePromotionRequestDto.getPromotionStartAt();
        if (updatePromotionRequestDto.getPromotionEndAt() != null) this.promotionEndAt = updatePromotionRequestDto.getPromotionEndAt();
        if (updatePromotionRequestDto.getMainExpose() != null) this.mainExpose = updatePromotionRequestDto.getMainExpose();
        if (updatePromotionRequestDto.getPromotionDetailContent() != null) this.promotionDetailContent = updatePromotionRequestDto.getPromotionDetailContent();
        if (updatePromotionRequestDto.getPromotionStatus() != null) this.promotionStatus = updatePromotionRequestDto.getPromotionStatus();
    }

    public void softDelete() {
        this.deleted = true;
    }
}