package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateMiddleCategoryRequestVo {

    private UUID middleCategoryId;

    private String name;

    private UUID topCategoryId;

    @Builder
    public UpdateMiddleCategoryRequestVo(UUID middleCategoryId, String name, UUID topCategoryId) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
    }
}
