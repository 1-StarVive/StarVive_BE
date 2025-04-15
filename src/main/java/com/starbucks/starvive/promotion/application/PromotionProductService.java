package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.RegisterPromotionProductRequestDto;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionProductRequestDto;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface PromotionProductService {

    void addPromotionProduct(RegisterPromotionProductRequestDto promotionWithProductRequest);

    List<PromotionProductResponseDto> getPromotionProducts(UUID promotionId);

    // void updatePromotion(UpdatePromotionProductRequestDto updatePromotionProductRequestDto);
}
