package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.BottomCategoryService;
import com.starbucks.starvive.category.application.MiddleCategoryService;
import com.starbucks.starvive.category.application.TopCategoryService;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final TopCategoryService topCategoryService;
    private final MiddleCategoryService middleCategoryService;
    private final BottomCategoryService bottomCategoryService;

    @Operation(summary = "상위 카테고리 조회",
            description = "상위 카테고리를 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/top")
    public List<TopCategoryResponse> getTopCategories() {
        return topCategoryService.findTopCategories();
    }

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 조회",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 조회합니다.",
            tags = {"middle-category-service"})
    @GetMapping("/middle")
    public List<MiddleCategoryResponse> getMiddleCategories(@RequestParam("topId") UUID topCategoryId) {
        return middleCategoryService.findMiddleCategories(topCategoryId);
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 조회",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리(필터)를 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping("/bottom")
    public List<BottomCategoryResponse> getBottomCategories(@RequestParam("middleId") UUID middleCategoryId) {
        return bottomCategoryService.findBottomCategories(middleCategoryId);
    }
}
