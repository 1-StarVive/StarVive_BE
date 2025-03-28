package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.CategoryService;
import com.starbucks.starvive.category.dto.out.CategoryListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryListResponseDTO> getCategories() {
//        return categoryService.getCategories();

        return List.of();
    }
}
