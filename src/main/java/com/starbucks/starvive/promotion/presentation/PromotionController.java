package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.promotion.application.PromotionService;
import com.starbucks.starvive.promotion.dto.out.PromotionTitleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/promotions")
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

//    @Operation(summary = "기획전 등록",
//            description = "기획전을 등록합니다.",
//            tags = {"promotion-service"})
//    @PostMapping("/add")
//    public ErrorResponse<String> addPromotion(
//            @RequestBody PromotionRequest promotionRequest
//    ) {
//        promotionService.addPromotion(promotionRequest);
//
//        return new ErrorResponse<>("기획전 등록 완료");
//    }

    @Operation(summary = "기획전 ID를 통해 하나만 조회",
            description = "기획전 ID를 통해 하나만 조회합니다.",
            tags = {"promotion-service"})
    @GetMapping("/{promotionId}")
    public List<PromotionTitleResponse> getPromotion(
            @PathVariable UUID promotionId
    ) {
        return promotionService.getProductsByPromotionId(promotionId);
    }

    @Operation(summary = "기획전 등록",
            description = "기획전을 등록합니다.",
            tags = {"promotion-service"})
    @GetMapping("/list")
    public List<PromotionTitleResponse> listPromotions() {
        return promotionService.findAllPromotions();
    }

//    @Operation(summary = "기획전 수정",
//            description = "기획전을 수정합니다.",
//            tags = {"promotion-service"})
//    @PutMapping("/{promotionId}")
//    public ErrorResponse<String> updatePromotion(
//            @PathVariable UUID promotionId,
//            @RequestBody PromotionRequest promotionRequest
//    ) {
//        promotionService.updatePromotion(promotionId, promotionRequest);
//        return new ErrorResponse<>("기획전 수정 완료");
//    }

//    @Operation(summary = "기획전 삭제",
//            description = "기획전을 삭제합니다.",
//            tags = {"promotion-service"})
//    @DeleteMapping("/{promotionId}")
//    public ErrorResponse<String> deletePromotion(
//            @PathVariable UUID promotionId
//    ) {
//        promotionService.deletePromotion(promotionId);
//        return new ErrorResponse<>("기획전 삭제 완료");
//    }
}
