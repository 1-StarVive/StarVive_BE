package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class BottomCategoryRequestVo {

    private UUID bottomCategoryId;

    private String name;

    private UUID middleCategoryId;

    @Builder
    public BottomCategoryRequestVo(UUID bottomCategoryId, String name, UUID middleCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.middleCategoryId = middleCategoryId;
    }
}
