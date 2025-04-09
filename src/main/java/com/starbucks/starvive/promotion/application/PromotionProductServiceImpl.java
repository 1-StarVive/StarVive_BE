package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import com.starbucks.starvive.promotion.infrastructure.PromotionProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromotionProductServiceImpl implements PromotionProductService {

    private final PromotionProductRepository promotionProductRepository;

    @Override
    public void addPromotionProduct(PromotionWithProductRequest promotionWithProductRequest) {
        UUID promotionId = promotionWithProductRequest.getPromotionId();

        List<PromotionProduct> promotionProducts = promotionWithProductRequest.getProductIds().stream()
                .map(productId -> PromotionProduct.builder()
                        .promotionId(promotionId)
                        .productId(productId)
                        .build())
                .toList();

        promotionProductRepository.saveAll(promotionProducts);
    }
}
