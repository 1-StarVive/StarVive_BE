package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import com.starbucks.starvive.promotion.application.PromotionProductService;
import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/promotion-products")
@RestController
@RequiredArgsConstructor
public class PromotionProductController {

    private final PromotionProductService promotionProductService;

    @PostMapping("/add")
    public BaseResponseEntity<String> addPromotionProduct(
            @RequestBody PromotionWithProductRequest promotionWithProductRequest
    ) {
        promotionProductService.addPromotionProduct(promotionWithProductRequest);

        return new BaseResponseEntity<>("기획전 상품 등록 완료");
    }
}
