package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.BottomCategoryService;
import com.starbucks.starvive.category.dto.in.BottomCategoryRequest;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/bottomCategories")
@RestController
@RequiredArgsConstructor
public class BottomCategoryController {

    private final BottomCategoryService bottomCategoryService;

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 등록",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 등록합니다.",
            tags = {"bottom-category-service"})
    @PostMapping("/add")
    public BaseResponseEntity<String> addBottomCategory(
            @RequestBody BottomCategoryRequest bottomCategoryRequest
            ) {
        bottomCategoryService.addBottomCategory(bottomCategoryRequest);

        return new BaseResponseEntity<>("하위 카테고리 등록 완료");
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 전체 조회",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 전체 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping("/all")
    public List<BottomCategoryResponse> getAllBottomCategories() {
        return bottomCategoryService.findBottomCategories();
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 조회",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리(필터)를 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping("/{middleCategoryId}")
    public List<BottomCategoryResponse> getBottomCategories(@PathVariable UUID middleCategoryId) {
        return bottomCategoryService.findBottomCategories(middleCategoryId);
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 수정",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 수정합니다.",
            tags = {"bottom-category-service"})
    @PutMapping("/{bottomCategoryId}")
    public BaseResponseEntity<String> updateBottomCategory(
            @PathVariable UUID bottomCategoryId,
            @RequestBody BottomCategoryRequest bottomCategoryRequest
    ) {
        bottomCategoryService.updateBottomCategory(bottomCategoryId, bottomCategoryRequest);
        return new BaseResponseEntity<>("하위 카테고리 수정 완료");
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 삭제",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 삭제합니다.",
            tags = {"bottom-category-service"})
    @DeleteMapping("/{bottomCategoryId}")
    public BaseResponseEntity<String> deleteBottomCategory(
            @PathVariable UUID bottomCategoryId
    ) {
        bottomCategoryService.deleteBottomCategory(bottomCategoryId);
        return new BaseResponseEntity<>("하위 카테고리 삭제 완료");
    }
}
