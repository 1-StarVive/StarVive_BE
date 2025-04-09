package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.BottomCategoryRequest;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import java.util.List;
import java.util.UUID;

public interface BottomCategoryService {

    void addBottomCategory(BottomCategoryRequest bottomCategoryRequest);

    List<BottomCategoryResponse> findBottomCategories(UUID middleCategoryId);

    List<BottomCategoryResponse> findBottomCategories();

    void updateBottomCategory(UUID bottomCategoryId, BottomCategoryRequest bottomCategoryRequest);

    void deleteBottomCategory(UUID bottomCategoryId);
}
