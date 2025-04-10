package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.vo.PromotionProductVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PromotionWithProductRequest {

    private UUID promotionId;

    private List<UUID> productIds;

    public PromotionProduct toPromotion() {
        return PromotionProduct.builder()
                .promotionId(promotionId)
                .build();
    }

    public static PromotionWithProductRequest from(PromotionProductVo promotionProductVo) {
        return PromotionWithProductRequest.builder()
                .promotionId(promotionProductVo.getPromotionId())
                .productIds(promotionProductVo.getProductIds())
                .build();
    }
}
