package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponse;
import com.starbucks.starvive.promotion.infrastructure.PromotionProductCustomRepository;
import com.starbucks.starvive.promotion.infrastructure.PromotionProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromotionProductServiceImpl implements PromotionProductService {

    private final PromotionProductRepository promotionProductRepository;

    private final PromotionProductCustomRepository promotionProductCustomRepository;

    @Transactional
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

    @Override
    public List<PromotionProductResponse> getPromotionProducts(UUID promotionId) {
        return promotionProductCustomRepository.findAllPromotionProducts(promotionId);
    }

    @Transactional
    @Override
    public void updatePromotion(PromotionWithProductRequest promotionWithProductRequest) {

        promotionProductRepository.deleteByPromotionId(promotionWithProductRequest.getPromotionId());

        // 2. 새롭게 받은 productIds 기준으로 다시 등록
        List<PromotionProduct> newProducts = promotionWithProductRequest.getProductIds().stream()
                .map(productId -> PromotionProduct.builder()
                        .promotionId(promotionWithProductRequest.getPromotionId())
                        .productId(productId)
                        .build())
                .toList();

        promotionProductRepository.saveAll(newProducts);
    }
}
