package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.BottomCategoryService;
import com.starbucks.starvive.category.dto.in.BottomCategoryRequest;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.vo.BottomCategoryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/bottom-categories")
@RestController
@RequiredArgsConstructor
public class BottomCategoryController {

    private final BottomCategoryService bottomCategoryService;

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 등록",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 등록합니다.",
            tags = {"bottom-category-service"})
    @PostMapping("/add")
    public void addBottomCategory(
            @RequestBody BottomCategoryRequestVo bottomCategoryRequestVo
            ) {
        bottomCategoryService.addBottomCategory(BottomCategoryRequest.from(bottomCategoryRequestVo));
    }

    @Operation(summary = "하위 카테고리 ID 조회",
            description = "하위 카테고리 ID 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping("/{bottomCategoryId}")
    public BottomCategoryResponse getBottomCategory (
            @PathVariable UUID bottomCategoryId
    ) {
        return bottomCategoryService.findBottomCategoryById(bottomCategoryId);
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
    @GetMapping
    public List<BottomCategoryResponse> getBottomCategories(@RequestParam("middleId") UUID middleCategoryId) {
        return bottomCategoryService.findBottomCategories(middleCategoryId);
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 수정",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 수정합니다.",
            tags = {"bottom-category-service"})
    @PutMapping("/{bottomCategoryId}")
    public void updateBottomCategory(
            @RequestBody BottomCategoryRequestVo bottomCategoryRequestVo
    ) {
        bottomCategoryService.updateBottomCategory(BottomCategoryRequest.from(bottomCategoryRequestVo) );
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 삭제",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 삭제합니다.",
            tags = {"bottom-category-service"})
    @DeleteMapping("/{bottomCategoryId}")
    public void deleteBottomCategory(
            @PathVariable UUID bottomCategoryId
    ) {
        bottomCategoryService.deleteBottomCategory(bottomCategoryId);
    }
}
