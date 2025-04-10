package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.vo.PromotionProductVo;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class PromotionWithProductRequest {

    private UUID promotionProductId;

    private UUID promotionId;

    private List<UUID> productIds;

    public PromotionProduct toPromotion() {
        return PromotionProduct.builder()
                .promotionProductId(promotionProductId)
                .promotionId(promotionId)
                .build();
    }

    public static PromotionWithProductRequest from(PromotionProductVo promotionProductVo) {
        return PromotionWithProductRequest.builder()
                .promotionProductId(promotionProductVo.getPromotionProductId())
                .promotionId(promotionProductVo.getPromotionId())
                .productIds(promotionProductVo.getProductIds())
                .build();
    }
}
