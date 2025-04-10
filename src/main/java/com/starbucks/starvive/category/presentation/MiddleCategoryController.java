package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.MiddleCategoryService;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequest;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/middleCategories")
@RestController
@RequiredArgsConstructor
public class MiddleCategoryController {

    private final MiddleCategoryService middleCategoryService;

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 등록",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 등록합니다.",
            tags = {"middle-category-service"})
    @PostMapping("/add")
    public BaseResponseEntity<String> addMiddleCategory(
            @RequestBody MiddleCategoryRequest middleCategoryRequest
    ) {
        middleCategoryService.addMiddleCategory(middleCategoryRequest);

        return new BaseResponseEntity<>("중간 카테고리 등록 완료");
    }

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 조회",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 조회합니다.",
            tags = {"middle-category-service"})
    @GetMapping("/{topCategoryId}")
    public List<MiddleCategoryResponse> getMiddleCategories(@PathVariable UUID topCategoryId) {
        return middleCategoryService.findMiddleCategories(topCategoryId);
    }

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 전체 조회",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 전체 조회합니다.",
            tags = {"middle-category-service"})
    @GetMapping("/all")
    public List<MiddleCategoryResponse> getAllMiddleCategories() {
        return middleCategoryService.findMiddleCategories();
    }

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 수정",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 수정합니다.",
            tags = {"middle-category-service"})
    @PutMapping("/{middleCategoryId}")
    public BaseResponseEntity<String> updateMiddleCategory(
            @PathVariable UUID middleCategoryId,
            @RequestBody @Valid MiddleCategoryRequest middleCategoryRequest
    ) {
        middleCategoryService.updateMiddleCategory(middleCategoryId, middleCategoryRequest);
        return new BaseResponseEntity<>("중간 카테고리 수정 완료");
    }

    @Operation(summary = "상위 카테고리 기준 중간 카테고리 삭제",
            description = "상위 카테고리 ID를 기반으로 중간 카테고리(필터)를 삭제합니다.",
            tags = {"middle-category-service"})
    @DeleteMapping("/{middleCategoryId}")
    public BaseResponseEntity<String> deleteMiddleCategory(
            @PathVariable UUID middleCategoryId
    ) {
        middleCategoryService.deleteMiddleCategory(middleCategoryId);
        return new BaseResponseEntity<>("중간 카테고리 삭제 완료");
    }
}
