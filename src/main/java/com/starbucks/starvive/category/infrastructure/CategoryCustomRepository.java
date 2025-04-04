package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryCustomRepository {

    /**
     *
     * @param topCategoryId
     * @return
     */
    List<TopWithMiddleBottomCategoryResponse> findCategoryByTopCategoryId(UUID topCategoryId);

}
