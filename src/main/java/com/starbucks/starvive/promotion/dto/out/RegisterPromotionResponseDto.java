package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.domain.PromotionStatus;
import com.starbucks.starvive.promotion.vo.RegisterPromotionVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterPromotionResponseDto {

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    @Builder
    public RegisterPromotionResponseDto(String title, String notice, LocalDate promotionStartAt,
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

    public static RegisterPromotionResponseDto from(Promotion promotion) {
        return RegisterPromotionResponseDto.builder()
                .title(promotion.getTitle())
                .notice(promotion.getNotice())
                .promotionStartAt(promotion.getPromotionStartAt())
                .promotionEndAt(promotion.getPromotionEndAt())
                .mainExpose(promotion.getMainExpose())
                .promotionDetailContent(promotion.getPromotionDetailContent())
                .promotionStatus(promotion.getPromotionStatus())
                .build();
    }
}
