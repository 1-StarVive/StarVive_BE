package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.TopCategoryService;
import com.starbucks.starvive.category.dto.in.TopCategoryForm;
import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryListResponse;
import com.starbucks.starvive.category.vo.TopCategoryVo;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/topCategories")
@RestController
@RequiredArgsConstructor
public class TopCategoryController {

    private final TopCategoryService topCategoryService;

    @Operation(summary = "상위 카테고리 등록", description = "상위 카테고리를 등록합니다.",
            tags = {"top-category-service"})
    @PostMapping(value = "/add")
    public BaseResponseEntity<String> addTopCategory(
            @ModelAttribute TopCategoryForm topCategoryForm
    ) {

        TopCategoryVo vo = TopCategoryVo.builder()
                .name(topCategoryForm.getName())
                .build();

        topCategoryService.addTopCategory(TopCategoryRequest.from(vo));

        return new BaseResponseEntity<>("상위 카테고리 등록 완료");
    }

    @Operation(summary = "상위 카테고리 조회", description = "상위 카테고리를 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/all")
    public List<TopCategoryListResponse> getTopCategories() {
        return topCategoryService.findTopCategories();
    }

    @Operation(summary = "상위 카테고리 ID 하나만 조회",
            description = "상위 카테고리 ID를 통해 하나만 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/{topCategoryId}")
    public List<TopCategoryListResponse> getTopCategory(@PathVariable UUID topCategoryId) {
        return topCategoryService.findTopCategoriesId(topCategoryId);
    }

    @Operation(summary = "상위 카테고리 수정", description = "상위 카테고리를 수정합니다.",
            tags = {"top-category-service"})
    @PutMapping("/{topCategoryId}")
    public BaseResponseEntity<String> updateTopCategory(
            @PathVariable UUID topCategoryId,
            @RequestBody @Valid TopCategoryRequest topCategoryRequest
    ) {
        topCategoryService.updateTopCategory(topCategoryId, topCategoryRequest);
        return new BaseResponseEntity<>("상위 카테고리 수정 완료");
    }

    @Operation(summary = "상위 카테고리 삭제", description = "상위 카테고리를 삭제합니다.",
            tags = {"top-category-service"})
    @DeleteMapping("/{topCategoryId}")
    public BaseResponseEntity<String> deleteTopCategory(
            @PathVariable UUID topCategoryId
    ) {
        topCategoryService.deleteTopCategory(topCategoryId);
        return new BaseResponseEntity<>("상위 카테고리 삭제 완료");
    }
}
