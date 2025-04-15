package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ListPromotionVo {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private String promotionDetailContent;

    @Builder
    public ListPromotionVo(String title, String notice, LocalDate promotionStartAt,
                           LocalDate promotionEndAt,
                           String promotionDetailContent) {
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.promotionDetailContent = promotionDetailContent;
    }
}
