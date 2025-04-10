package com.starbucks.starvive.promotion.infrastructure;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PromotionProductRepository extends JpaRepository<PromotionProduct, UUID> {

}
