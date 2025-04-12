package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeletePromotionRequestVo {

    private UUID promotionId;

    @Builder
    public DeletePromotionRequestVo(UUID promotionId) {
        this.promotionId = promotionId;
    }
}
