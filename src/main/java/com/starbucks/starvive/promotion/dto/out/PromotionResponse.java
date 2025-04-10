package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.PromotionStatus;
import com.starbucks.starvive.promotion.vo.PromotionVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PromotionResponse {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    @Builder
    public PromotionResponse(String title, String notice, LocalDate promotionStartAt,
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

    public static PromotionResponse from(PromotionVo promotionVo) {
        return PromotionResponse.builder()
                .title(promotionVo.getTitle())
                .notice(promotionVo.getNotice())
                .promotionStartAt(promotionVo.getPromotionStartAt())
                .promotionEndAt(promotionVo.getPromotionEndAt())
                .mainExpose(promotionVo.getMainExpose())
                .promotionDetailContent(promotionVo.getPromotionDetailContent())
                .promotionStatus(promotionVo.getPromotionStatus())
                .build();
    }
}
