package com.starbucks.starvive.promotion.presentation;

import com.starbucks.starvive.promotion.application.PromotionService;
import com.starbucks.starvive.promotion.dto.in.DeletePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.PromotionRequestDto;
import com.starbucks.starvive.promotion.dto.in.UpdatePromotionRequestDto;
import com.starbucks.starvive.promotion.dto.out.ListPromotionResponseDto;
import com.starbucks.starvive.promotion.vo.DeletePromotionRequestVo;
import com.starbucks.starvive.promotion.vo.UpdatePromotionRequestVo;
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

    @Operation(summary = "기획전 등록",
            description = "기획전을 등록합니다.",
            tags = {"promotion-service"})
    @PostMapping("/add")
    public void addPromotion(
            @RequestBody PromotionRequestDto promotionRequest
    ) {
        promotionService.addPromotion(promotionRequest);
    }

    @Operation(summary = "기획전 ID를 통해 하나만 조회",
            description = "기획전 ID를 통해 하나만 조회합니다.",
            tags = {"promotion-service"})
    @GetMapping
    public List<ListPromotionResponseDto> getPromotion(
            @RequestParam("promotionId") UUID promotionId
    ) {
        return promotionService.getProductsByPromotionId(promotionId);
    }

    @Operation(summary = "기획전 전체 조회;",
            description = "기획전을 전체 조회합니다.",
            tags = {"promotion-service"})
    @GetMapping("/list")
    public List<ListPromotionResponseDto> listPromotions() {
        return promotionService.findAllPromotions();
    }

    @Operation(summary = "기획전 수정",
            description = "기획전을 수정합니다.",
            tags = {"promotion-service"})
    @PutMapping
    public void updatePromotion(
            @RequestBody UpdatePromotionRequestVo updatePromotionRequestVo
            ) {
        promotionService.updatePromotion(UpdatePromotionRequestDto.from(updatePromotionRequestVo));
    }

    @Operation(summary = "기획전 삭제",
            description = "기획전을 삭제합니다.",
            tags = {"promotion-service"})
    @DeleteMapping
    public void deletePromotion(
            @RequestBody DeletePromotionRequestVo deletePromotionRequestVo
    ) {
        promotionService.deletePromotion(DeletePromotionRequestDto.from(deletePromotionRequestVo));
    }
}
