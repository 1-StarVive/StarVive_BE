package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MiddleCategoryRequest {

    private String name;

    private UUID topCategoryId;

    public MiddleCategory toEntity() {
        return MiddleCategory.builder()
                .name(name)
                .topCategoryId(topCategoryId)
                .build();
    }
}
