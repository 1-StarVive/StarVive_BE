package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.domain.PromotionStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PromotionRequest {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    public Promotion toPromotion() {
        return Promotion.builder()
                .title(title)
                .notice(notice)
                .promotionStartAt(promotionStartAt)
                .promotionEndAt(promotionEndAt)
                .mainExpose(mainExpose)
                .promotionDetailContent(promotionDetailContent)
                .promotionStatus(promotionStatus)
                .build();
    }
}
