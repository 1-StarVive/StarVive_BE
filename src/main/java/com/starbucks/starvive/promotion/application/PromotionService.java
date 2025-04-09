package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.PromotionRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionTitleResponse;

import java.util.List;

public interface PromotionService {

    void addPromotion(PromotionRequest promotionRequest);

    List<PromotionTitleResponse> findAllPromotions();
}
