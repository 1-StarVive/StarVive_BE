package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.vo.MiddleCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MiddleCategoryRequestDto {

    private UUID middleCategoryId;

    private String name;

    private UUID topCategoryId;

    @Builder
    public MiddleCategoryRequestDto(UUID middleCategoryId, String name, UUID topCategoryId) {
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

    public static MiddleCategoryRequestDto fromEntity(MiddleCategoryRequestVo middleCategoryVo) {
        return MiddleCategoryRequestDto.builder()
                .middleCategoryId(middleCategoryVo.getMiddleCategoryId())
                .name(middleCategoryVo.getName())
                .topCategoryId(middleCategoryVo.getTopCategoryId())
                .build();
    }
}