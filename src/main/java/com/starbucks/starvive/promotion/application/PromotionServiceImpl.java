package com.starbucks.starvive.promotion.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.promotion.domain.Promotion;
import com.starbucks.starvive.promotion.dto.in.DeletePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.PromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.out.ListPromotionResponseDto;
import com.starbucks.starvive.promotion.infrastructure.PromotionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    // 타이틀 중복 체크 x
    // 타이틀은 똑같아도 괜찮지만 진행할지 말지 .. ?
    @Override
    public void addPromotion(PromotionRequestDto promotionRequest) {
        if (promotionRepository.findByTitle(promotionRequest.getTitle()).isPresent()) {
            throw new BaseException(DUPLICATED_PROMOTION);
        }

        promotionRepository.save(promotionRequest.toPromotion());
    }

    @Override
    public List<ListPromotionResponseDto> getProductsByPromotionId(UUID promotionId) {
        return promotionRepository.findByPromotionIdAndDeletedFalse(promotionId)
                .stream().map(ListPromotionResponseDto::from).toList();
    }

    @Override
    public List<ListPromotionResponseDto> findAllPromotions() {
        return promotionRepository.findAllByDeletedFalse()
                .stream()
                .map(ListPromotionResponseDto::from)
                .toList();
    }

    // update 부분 다시 수정 - 2025.04.10 
    @Transactional
    @Override
    public void updatePromotion(UpdatePromotionRequestDto updatePromotionRequestDto) {
        Promotion promotion = promotionRepository.findById(updatePromotionRequestDto.getPromotionId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PROMOTION));

        promotion.update(updatePromotionRequestDto);
    }

    @Transactional
    @Override
    public void deletePromotion(DeletePromotionRequestDto deletePromotionRequestDto) {
        Promotion promotion = promotionRepository.findById(deletePromotionRequestDto.getPromotionId())
                .orElseThrow(() -> new BaseException(PROMOTION_PRODUCT_DELETE_FAIL));

        promotion.softDelete();
    }
}
