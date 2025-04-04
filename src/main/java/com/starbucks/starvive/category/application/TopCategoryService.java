package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface TopCategoryService {

    /**
     *
     * @return
     */
    List<TopCategoryResponse> findTopCategories();

    /**
     *
     * @param topCategoryId
     * @return
     */
    List<TopWithMiddleBottomCategoryResponse> findCategoryByTopCategoryId(UUID topCategoryId);


}
