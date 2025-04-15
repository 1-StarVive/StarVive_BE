package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.domain.PromotionStatus;
import com.starbucks.starvive.promotion.vo.RegisterPromotionVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterPromotionRequestDto {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    @Builder
    public RegisterPromotionRequestDto(String title, String notice, LocalDate promotionStartAt,
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

    public static RegisterPromotionRequestDto fromPromotion(RegisterPromotionVo registerPromotionVo) {
        return RegisterPromotionRequestDto.builder()
                .title(registerPromotionVo.getTitle())
                .notice(registerPromotionVo.getNotice())
                .promotionStartAt(registerPromotionVo.getPromotionStartAt())
                .promotionEndAt(registerPromotionVo.getPromotionEndAt())
                .mainExpose(registerPromotionVo.getMainExpose())
                .promotionDetailContent(registerPromotionVo.getPromotionDetailContent())
                .promotionStatus(registerPromotionVo.getPromotionStatus())
                .build();
    }
}
