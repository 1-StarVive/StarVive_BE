package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteTopCategoryRequestVo {
    private UUID topCategoryId;

    @Builder
    public DeleteTopCategoryRequestVo(UUID topCategoryId) {
        this.topCategoryId = topCategoryId;
    }
}
