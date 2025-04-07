package com.starbucks.starvive.promotion.vo;

import com.starbucks.starvive.promotion.domain.PromotionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PromotionVo {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    @Builder
    public PromotionVo(String title, String notice, LocalDate promotionStartAt,
                       LocalDate promotionEndAt, Boolean mainExpose,
                       String promotionDetailContent, PromotionStatus promotionStatus) {
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.mainExpose = mainExpose;
        this.promotionDetailContent = promotionDetailContent;
        this.promotionStatus = promotionStatus;
    }
}
