package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.MiddleCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MiddleCategoryResponse {

    private UUID middleCategoryId;

    private String name;

    private UUID topCategoryId;

    @Builder
    public MiddleCategoryResponse(UUID middleCategoryId, String name, UUID topCategoryId) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
    }

    public static MiddleCategoryResponse from(MiddleCategory middleCategory) {
        return MiddleCategoryResponse.builder()
                .middleCategoryId(middleCategory.getMiddleCategoryId())
                .name(middleCategory.getName())
                .topCategoryId(middleCategory.getTopCategoryId())
                .build();
    }
}
