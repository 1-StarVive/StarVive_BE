package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.promotion.domain.PromotionProduct;
import com.starbucks.starvive.promotion.dto.in.RegisterPromotionProductRequestDto;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionProductRequestDto;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponseDto;
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
    public void addPromotionProduct(RegisterPromotionProductRequestDto promotionWithProductRequest) {
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
    public List<PromotionProductResponseDto> getPromotionProducts(UUID promotionId) {
        return promotionProductCustomRepository.findAllPromotionProducts(promotionId);
    }

//    @Transactional
//    @Override
//    public void updatePromotion(UpdatePromotionProductRequestDto updatePromotionProductRequestDto) {
//
//        promotionProductRepository.deleteByPromotionId(updatePromotionProductRequestDto.getPromotionId());
//
//        // 2. 새롭게 받은 productIds 기준으로 다시 등록
//        List<PromotionProduct> newProducts = updatePromotionProductRequestDto.getProductIds().stream()
//                .map(productId -> PromotionProduct.builder()
//                        .promotionId(updatePromotionProductRequestDto.getPromotionId())
//                        .productId(productId)
//                        .build())
//                .toList();
//
//        promotionProductRepository.saveAll(newProducts);
//    }
}
