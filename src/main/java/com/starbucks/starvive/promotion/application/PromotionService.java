package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.PromotionRequest;

public interface PromotionService {

    void addPromotion(PromotionRequest promotionRequest);
}
