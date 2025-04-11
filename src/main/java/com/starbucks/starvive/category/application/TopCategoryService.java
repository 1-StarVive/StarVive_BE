package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TopCategoryService {

    void addTopCategory(TopCategoryRequest topCategoryRequest);

    TopCategoryResponse findTopCategoriesId(UUID topCategoryId);

    List<TopCategoryResponse> findTopCategories();

    void updateTopCategory(TopCategoryRequest topCategoryRequest);

    void deleteTopCategory(UUID topCategoryId);
}
