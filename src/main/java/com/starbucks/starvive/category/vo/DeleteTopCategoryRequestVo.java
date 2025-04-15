package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteTopCategoryRequestVo {

    private UUID middleCategoryId;

    private UUID topCategoryId;

    private String name;

    @Builder
    public DeleteTopCategoryRequestVo(UUID middleCategoryId, UUID topCategoryId, String name) {
        this.middleCategoryId = middleCategoryId;
        this.topCategoryId = topCategoryId;
        this.name = name;
    }
}
