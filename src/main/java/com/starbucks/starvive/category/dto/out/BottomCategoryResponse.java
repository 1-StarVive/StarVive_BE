package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.BottomCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class BottomCategoryResponse {

    private UUID bottomCategoryId;

    private String name;

    private UUID middleCategoryId;

    @Builder
    public BottomCategoryResponse(UUID bottomCategoryId, String name, UUID middleCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.middleCategoryId = middleCategoryId;
    }

    public static BottomCategoryResponse from(BottomCategory bottomCategory) {
        return BottomCategoryResponse.builder()
                .bottomCategoryId(bottomCategory.getBottomCategoryId())
                .name(bottomCategory.getName())
                .middleCategoryId(bottomCategory.getMiddleCategoryId())
                .build();
    }
}
