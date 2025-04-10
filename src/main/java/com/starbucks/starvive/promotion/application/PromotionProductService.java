package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponse;

import java.util.List;
import java.util.UUID;

public interface PromotionProductService {

    void addPromotionProduct(PromotionWithProductRequest promotionWithProductRequest);

    List<PromotionProductResponse> getPromotionProducts(UUID promotionId);

    void updatePromotion(PromotionWithProductRequest promotionWithProductRequest);
}
