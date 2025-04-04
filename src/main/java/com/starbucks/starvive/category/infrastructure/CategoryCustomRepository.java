package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryCustomRepository {

    List<TopWithMiddleBottomCategoryResponse> findCategoryByTopCategoryId(UUID topCategoryId);

}
