package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.BottomCategoryService;
import com.starbucks.starvive.category.application.MiddleCategoryService;
import com.starbucks.starvive.category.application.TopCategoryService;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final TopCategoryService topCategoryService;
    private final MiddleCategoryService middleCategoryService;
    private final BottomCategoryService bottomCategoryService;

    @GetMapping("/top")
    public List<TopCategoryResponse> getTopCategories() {
        return topCategoryService.findTopCategories();
    }

    @GetMapping("/{topCategoryId}/middle")
    public List<MiddleCategoryResponse> getMiddleCategories(@PathVariable UUID topCategoryId) {
        return middleCategoryService.findMiddleCategories(topCategoryId);
    }

    @GetMapping("/{middleCategoryId}/bottom")
    public List<BottomCategoryResponse> getBottomCategories(@PathVariable UUID middleCategoryId) {
        return bottomCategoryService.findBottomCategories(middleCategoryId);
    }

    @GetMapping("/{topCategoryId}")
    public List<TopWithMiddleBottomCategoryResponse> getCategoryByTopId(@PathVariable UUID topCategoryId) {
        return topCategoryService.findCategoryByTopCategoryId(topCategoryId);
    }
}
