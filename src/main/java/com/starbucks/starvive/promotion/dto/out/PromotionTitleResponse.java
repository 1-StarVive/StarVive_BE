package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PromotionTitleResponse {

    private UUID promotionId;

    private String title;

    private String promotionDetailContent;

    @Builder
    public PromotionTitleResponse(UUID promotionId, String title,
                                  String promotionDetailContent) {
        this.promotionId = promotionId;
        this.title = title;
        this.promotionDetailContent = promotionDetailContent;
    }

    public static PromotionTitleResponse from(Promotion promotion) {
        return PromotionTitleResponse.builder()
                .promotionId(promotion.getPromotionId())
                .title(promotion.getTitle())
                .promotionDetailContent(promotion.getPromotionDetailContent())
                .build();
    }
}
