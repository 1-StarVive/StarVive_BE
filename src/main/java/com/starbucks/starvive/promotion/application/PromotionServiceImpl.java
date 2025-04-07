package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.dto.in.PromotionRequest;
import com.starbucks.starvive.promotion.infrastructure.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.DUPLICATED_PROMOTION;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private PromotionRepository promotionRepository;

    @Override
    public void addPromotion(PromotionRequest promotionRequest) {
        if(promotionRepository.findByTitle(promotionRequest.getTitle()).isPresent()) {
            throw new BaseException(DUPLICATED_PROMOTION);
        }

        Promotion promotion = promotionRequest.toPromotion();
        promotionRepository.save(promotion);
    }
}
