package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.DeletePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.RegisterPromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.out.ListPromotionResponseDto;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    void addPromotion(RegisterPromotionRequestDto promotionRequest);

    List<ListPromotionResponseDto> getProductsByPromotionId(UUID promotionId);

    List<ListPromotionResponseDto> findAllPromotions();

    void updatePromotion(UpdatePromotionRequestDto updatePromotionRequestDto);

    void deletePromotion(DeletePromotionRequestDto deletePromotionRequestDto);
}
