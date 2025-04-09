package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;

public interface PromotionProductService {

    void addPromotionProduct(PromotionWithProductRequest promotionWithProductRequest);
}
