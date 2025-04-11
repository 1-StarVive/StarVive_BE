package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.vo.DeleteBottomCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteBottomCategoryRequestDto {

    private UUID bottomCategoryId;

    @Builder
    public DeleteBottomCategoryRequestDto(UUID bottomCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
    }

    public BottomCategory toCategory() {
        return BottomCategory.builder()
                .bottomCategoryId(bottomCategoryId)
                .build();
    }

    public static DeleteBottomCategoryRequestDto from(DeleteBottomCategoryRequestVo deleteBottomCategoryRequestVo) {
        return DeleteBottomCategoryRequestDto.builder()
                .bottomCategoryId(deleteBottomCategoryRequestVo.getBottomCategoryId())
                .build();
    }
}
