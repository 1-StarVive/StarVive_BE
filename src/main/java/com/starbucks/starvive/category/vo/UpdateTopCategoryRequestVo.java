package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateTopCategoryRequestVo {

    private UUID topCategoryId;

    private String name;

    @Builder
    public UpdateTopCategoryRequestVo(UUID topCategoryId, String name) {
        this.topCategoryId = topCategoryId;
        this.name = name;
    }
}
