package com.starbucks.starvive.category.vo;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.dto.in.DeleteBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.DeleteMiddleCategoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteBottomCategoryRequestVo {

    private UUID bottomCategoryId;

    @Builder
    public DeleteBottomCategoryRequestVo(UUID bottomCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
    }

    public BottomCategory toEntity() {
        return BottomCategory.builder()
                .build();
    }

    public static DeleteBottomCategoryRequestDto fromEntity(DeleteBottomCategoryRequestVo deleteBottomCategoryRequestVo) {
        return DeleteBottomCategoryRequestDto.builder()
                .bottomCategoryId(deleteBottomCategoryRequestVo.getBottomCategoryId())
                .build();
    }
}
