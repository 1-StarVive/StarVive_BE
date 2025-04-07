package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import com.starbucks.starvive.promotion.application.PromotionService;
import com.starbucks.starvive.promotion.dto.in.PromotionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/promotions")
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/add")
    public BaseResponseEntity<String> addPromotion(
            @RequestBody PromotionRequest promotionRequest
    ) {
        promotionService.addPromotion(promotionRequest);

        return new BaseResponseEntity<>("기획전 등록 완료");
    }
}
