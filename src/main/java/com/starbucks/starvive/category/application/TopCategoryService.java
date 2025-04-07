package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TopCategoryService {

    void addTopCategory(TopCategoryRequest topCategoryRequest);

    List<TopCategoryListResponse> findTopCategories();

    void updateTopCategory(UUID topCategoryId, TopCategoryRequest topCategoryRequest);

    void deleteTopCategory(UUID topCategoryId);
}
