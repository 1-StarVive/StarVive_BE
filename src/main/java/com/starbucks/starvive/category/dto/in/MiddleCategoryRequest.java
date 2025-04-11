package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.vo.MiddleCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MiddleCategoryRequest {

    private UUID middleCategoryId;

    private String name;

    private UUID topCategoryId;

    @Builder
    public MiddleCategoryRequest(UUID middleCategoryId, String name, UUID topCategoryId) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
    }

    public MiddleCategory toEntity() {
        return MiddleCategory.builder()
                .name(name)
                .topCategoryId(topCategoryId)
                .build();
    }

    public static MiddleCategoryRequest fromEntity(MiddleCategoryRequestVo middleCategoryVo) {
        return MiddleCategoryRequest.builder()
                .middleCategoryId(middleCategoryVo.getMiddleCategoryId())
                .name(middleCategoryVo.getName())
                .topCategoryId(middleCategoryVo.getTopCategoryId())
                .build();
    }
}