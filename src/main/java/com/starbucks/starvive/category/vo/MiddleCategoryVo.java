package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MiddleCategoryVo {

    private UUID bottomCategoryId;

    private String name;

    private UUID topCategoryId;

    @Builder
    public MiddleCategoryVo(UUID bottomCategoryId, String name, UUID topCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
    }
}
