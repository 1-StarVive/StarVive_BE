package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.vo.RegisterPromotionProductVo;
import com.starbucks.starvive.promotion.vo.UpdatePromotionProductVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdatePromotionProductRequestDto {

    private UUID promotionId;

    private List<UUID> productIds;

    @Builder
    public UpdatePromotionProductRequestDto(UUID promotionId, List<UUID> productIds) {
        this.promotionId = promotionId;
        this.productIds = productIds;
    }

    public PromotionProduct toPromotion() {
        return PromotionProduct.builder()
                .promotionId(promotionId)
                .build();
    }

    public static RegisterPromotionProductRequestDto from(UpdatePromotionProductVo updatePromotionProductVo) {
        return RegisterPromotionProductRequestDto.builder()
                .promotionId(updatePromotionProductVo.getPromotionId())
                .productIds(updatePromotionProductVo.getProductIds())
                .build();
    }
}
