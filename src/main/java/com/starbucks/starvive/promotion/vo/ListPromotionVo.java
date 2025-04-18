package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListPromotionVo {

    private UUID promotionId;

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private String promotionDetailContent;

    @Builder
    public ListPromotionVo(UUID promotionId, String title, String notice, LocalDate promotionStartAt,
                           LocalDate promotionEndAt,
                           String promotionDetailContent) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.promotionDetailContent = promotionDetailContent;
    }
}
