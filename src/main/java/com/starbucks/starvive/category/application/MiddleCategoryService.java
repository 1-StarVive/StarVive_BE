package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequest;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface MiddleCategoryService {

    void addMiddleCategory(MiddleCategoryRequest middleCategoryRequest);

    List<MiddleCategoryResponse> findMiddleCategories(UUID topCategoryId);

    List<MiddleCategoryResponse> findMiddleCategories();

    void updateMiddleCategory(UUID middleCategoryId, MiddleCategoryRequest middleCategoryRequest);

    void deleteMiddleCategory(UUID middleCategoryId);
}
