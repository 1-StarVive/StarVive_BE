package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromotionTitleResponse {

    private String title;

    @Builder
    public PromotionTitleResponse(String title) {
        this.title = title;
    }

    public static PromotionTitleResponse from(Promotion promotion) {
        return new PromotionTitleResponse(promotion.getTitle());
    }
}
