package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeletePromotionVo {

    private UUID promotionId;

    @Builder
    public DeletePromotionVo(UUID promotionId) {
        this.promotionId = promotionId;
    }
}
