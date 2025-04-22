package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.promotion.application.PromotionProductService;
import com.starbucks.starvive.promotion.dto.in.RegisterPromotionProductRequestDto;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponseDto;
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
    public void addPromotionProduct(
            @RequestBody RegisterPromotionProductRequestDto promotionWithProductRequest
    ) {
        promotionProductService.addPromotionProduct(promotionWithProductRequest);
    }

    @Operation(summary = "기획전 ID 상품 조회",
            description = "기획전 ID를 통해 상품을 조회힙니다",
            tags = {"promotion-product-service"})
    @GetMapping
    public List<PromotionProductResponseDto> getPromotionProduct(
            @RequestParam("promotionId") UUID promotionId
    ) {
        return promotionProductService.getPromotionProducts(promotionId);
    }
}
