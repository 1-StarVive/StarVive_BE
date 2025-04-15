package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.vo.RegisterPromotionProductVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterPromotionProductRequestDto {

    private UUID promotionId;

    private List<UUID> productIds;

    @Builder
    public RegisterPromotionProductRequestDto(UUID promotionId, List<UUID> productIds) {
        this.promotionId = promotionId;
        this.productIds = productIds;
    }

    public PromotionProduct toPromotion() {
        return PromotionProduct.builder()
                .promotionId(promotionId)
                .build();
    }

    public static RegisterPromotionProductRequestDto from(RegisterPromotionProductVo promotionProductVo) {
        return RegisterPromotionProductRequestDto.builder()
                .promotionId(promotionProductVo.getPromotionId())
                .productIds(promotionProductVo.getProductIds())
                .build();
    }
}
