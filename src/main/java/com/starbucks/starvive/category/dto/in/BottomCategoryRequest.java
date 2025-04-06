package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.BottomCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BottomCategoryRequest {

    private String name;

    private UUID middleCategoryId;

    public BottomCategory toCategory() {
        return BottomCategory.builder()
                .name(name)
                .middleCategoryId(middleCategoryId)
                .build();
    }
}
