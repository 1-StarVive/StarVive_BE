package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.dto.in.PromotionRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionTitleResponse;
import com.starbucks.starvive.promotion.infrastructure.PromotionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.DUPLICATED_PROMOTION;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.PROMOTION_PRODUCT_DELETE_FAIL;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public void addPromotion(PromotionRequest promotionRequest) {
        if (promotionRepository.findByTitle(promotionRequest.getTitle()).isPresent()) {
            throw new BaseException(DUPLICATED_PROMOTION);
        }

        Promotion promotion = promotionRequest.toPromotion();
        promotionRepository.save(promotion);
    }

    @Override
    public List<PromotionTitleResponse> getProductsByPromotionId(UUID promotionId) {
        return promotionRepository.findByPromotionIdAndDeletedFalse(promotionId)
                .stream().map(PromotionTitleResponse::from).toList();
    }

    @Override
    public List<PromotionTitleResponse> findAllPromotions() {
        return promotionRepository.findAllByDeletedFalse()
                .stream()
                .map(PromotionTitleResponse::from)
                .toList();
    }

    @Transactional
    @Override
    public void updatePromotion(UUID promotionId, PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new BaseException(DUPLICATED_PROMOTION));

        promotion.update(promotionRequest);
    }

    @Transactional
    @Override
    public void deletePromotion(UUID promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new BaseException(PROMOTION_PRODUCT_DELETE_FAIL));

        promotion.softDelete();
    }
}
