package com.starbucks.starvive.promotion.infrastructure;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, UUID> {

    void deleteByPromotionId(UUID promotionId);
}
