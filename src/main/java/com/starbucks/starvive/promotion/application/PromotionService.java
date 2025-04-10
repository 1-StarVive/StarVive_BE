package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.PromotionRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionTitleResponse;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    void addPromotion(PromotionRequest promotionRequest);

    List<PromotionTitleResponse> getProductsByPromotionId(UUID promotionId);

    List<PromotionTitleResponse> findAllPromotions();

    void updatePromotion(UUID promotionId, PromotionRequest promotionRequest);

    void deletePromotion(UUID promotionId);
}
