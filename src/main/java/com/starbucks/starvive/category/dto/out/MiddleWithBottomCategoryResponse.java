package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.domain.MiddleCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class MiddleWithBottomCategoryResponse {

    private UUID middleCategoryId;

    private String name;

    private List<BottomCategoryResponse> bottomCategories;

    @Builder
    public MiddleWithBottomCategoryResponse(UUID middleCategoryId,
                                            String name,
                                            List<BottomCategoryResponse> bottomCategories) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.bottomCategories = bottomCategories;
    }

    public static MiddleWithBottomCategoryResponse from(MiddleCategory middleCategory, List<BottomCategoryResponse> bottomCategory) {
        return MiddleWithBottomCategoryResponse.builder()
                .middleCategoryId(middleCategory.getMiddleCategoryId())
                .name(middleCategory.getName())
                .bottomCategories(bottomCategory)
                .build();
    }
}
