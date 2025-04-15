package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.vo.BottomCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class BottomCategoryRequestDto {

    private UUID bottomCategoryId;
    private String name;

    private UUID middleCategoryId;

    @Builder
    public BottomCategoryRequestDto(UUID bottomCategoryId, String name, UUID middleCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.middleCategoryId = middleCategoryId;
    }

    public BottomCategory toCategory() {
        return BottomCategory.builder()
                .name(name)
                .middleCategoryId(middleCategoryId)
                .build();
    }

    public static BottomCategoryRequestDto from(BottomCategoryRequestVo bottomCategoryVo) {
        return BottomCategoryRequestDto.builder()
                .bottomCategoryId(bottomCategoryVo.getBottomCategoryId())
                .name(bottomCategoryVo.getName())
                .middleCategoryId(bottomCategoryVo.getMiddleCategoryId())
                .build();
    }
}
