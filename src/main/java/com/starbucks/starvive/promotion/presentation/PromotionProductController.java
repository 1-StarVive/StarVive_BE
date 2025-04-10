package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import com.starbucks.starvive.promotion.application.PromotionProductService;
import com.starbucks.starvive.promotion.dto.in.PromotionWithProductRequest;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/promotion-products")
@RestController
@RequiredArgsConstructor
public class PromotionProductController {

    private final PromotionProductService promotionProductService;

    @Operation(summary = "기획전 상품 등록",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 조회합니다.",
            tags = {"promotion-product-service"})
    @PostMapping
    public BaseResponseEntity<String> addPromotionProduct(
            @RequestBody PromotionWithProductRequest promotionWithProductRequest
    ) {
        promotionProductService.addPromotionProduct(promotionWithProductRequest);

        return new BaseResponseEntity<>("기획전 상품 등록 완료");
    }

    @Operation(summary = "기획전 ID 상품 조회",
            description = "기획전 ID를 통해 상품을 조회힙니다",
            tags = {"promotion-product-service"})
    @GetMapping("/{promotionId}")
    public List<PromotionProductResponse> getPromotionProduct(
            @PathVariable UUID promotionId
    ) {
        return promotionProductService.getPromotionProducts(promotionId);
    }

    @Operation(summary = "기획전 ID 상품 수정",
            description = "기획전 ID를 통해 상품을 수정힙니다",
            tags = {"promotion-product-service"})
    @PutMapping("/{promotionId}")
    public BaseResponseEntity<String> updatePromotionProduct(
            @PathVariable UUID promotionId
    ) {
        return new BaseResponseEntity<>("기획전 상품 수정 완료");
    }
}
