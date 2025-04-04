package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface MiddleCategoryService {

    List<MiddleCategoryResponse> findMiddleCategories(UUID topCategoryId);
}
