package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.TopCategoryService;
import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.vo.TopCategoryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/top-categories")
@RestController
@RequiredArgsConstructor
public class TopCategoryController {

    private final TopCategoryService topCategoryService;

    @Operation(summary = "상위 카테고리 등록", description = "상위 카테고리를 등록합니다.",
            tags = {"top-category-service"})
    @PostMapping(value = "/add")
        public void addTopCategory(
            @RequestBody TopCategoryRequestVo topCategoryRequestVo
    ) {

        TopCategoryRequest requestDto = TopCategoryRequest.from(topCategoryRequestVo);
        topCategoryService.addTopCategory(requestDto);
    }

    @Operation(summary = "상위 카테고리 조회", description = "상위 카테고리를 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/all")
    public List<TopCategoryResponse> getTopCategories() {
        return topCategoryService.findTopCategories();
    }

    @Operation(summary = "상위 카테고리 ID 하나만 조회",
            description = "상위 카테고리 ID를 통해 하나만 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/{topCategoryId}")
    public TopCategoryResponse getTopCategory(@PathVariable UUID topCategoryId) {
        return topCategoryService.findTopCategoriesId(topCategoryId);
    }

    @Operation(summary = "상위 카테고리 수정", description = "상위 카테고리를 수정합니다.",
            tags = {"top-category-service"})
    @PutMapping
    public void updateTopCategory(
            @RequestBody TopCategoryRequestVo topCategoryRequestVo
    ) {
        topCategoryService.updateTopCategory(TopCategoryRequest.from(topCategoryRequestVo));
    }

    @Operation(summary = "상위 카테고리 삭제", description = "상위 카테고리를 삭제합니다.",
            tags = {"top-category-service"})
    @DeleteMapping("/{topCategoryId}")
    public void deleteTopCategory(
            @PathVariable UUID topCategoryId
    ) {
        topCategoryService.deleteTopCategory(topCategoryId);
    }
}