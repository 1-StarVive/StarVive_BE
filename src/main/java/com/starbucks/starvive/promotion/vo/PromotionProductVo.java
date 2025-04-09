package com.starbucks.starvive.promotion.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PromotionProductVo {

    private UUID promotionProductId;

    private UUID promotionId;

    private List<UUID> productIds;

    @Builder
    public PromotionProductVo(UUID promotionProductId, UUID promotionId,
                              List<UUID> productIds) {
        this.promotionProductId = promotionProductId;
        this.promotionId = promotionId;
        this.productIds = productIds;
    }
}
