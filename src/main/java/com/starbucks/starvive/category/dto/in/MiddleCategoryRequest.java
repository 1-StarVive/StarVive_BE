package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.vo.MiddleCategoryVo;
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

    public static MiddleCategoryRequest fromEntity(MiddleCategoryVo middleCategoryVo) {
        return MiddleCategoryRequest.builder()
                .name(middleCategoryVo.getName())
                .topCategoryId(middleCategoryVo.getTopCategoryId())
                .build();
    }
}
