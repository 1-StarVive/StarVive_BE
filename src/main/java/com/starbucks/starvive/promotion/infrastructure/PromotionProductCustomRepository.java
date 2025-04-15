package com.starbucks.starvive.promotion.infrastructure;

import com.starbucks.starvive.promotion.dto.out.PromotionProductResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PromotionProductCustomRepository {

    List<PromotionProductResponseDto> findAllPromotionProducts(UUID promotionId);
}
