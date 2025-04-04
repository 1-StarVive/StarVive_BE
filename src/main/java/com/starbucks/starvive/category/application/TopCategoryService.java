package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.TopCategoryResponse;

import java.util.List;

public interface TopCategoryService {

    // 상위 카테고리 등록


    // 상위 카테고리 조회
    List<TopCategoryResponse> findTopCategories();
}
