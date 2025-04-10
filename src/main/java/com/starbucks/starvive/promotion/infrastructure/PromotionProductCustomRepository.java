package com.starbucks.starvive.promotion.infrastructure;

import com.starbucks.starvive.promotion.dto.out.PromotionProductResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PromotionProductCustomRepository {

    List<PromotionProductResponse> findAllPromotionProducts(UUID promotionId);
}
