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


}
