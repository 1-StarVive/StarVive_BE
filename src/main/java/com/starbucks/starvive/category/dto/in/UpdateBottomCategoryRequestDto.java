package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.vo.BottomCategoryRequestVo;
import com.starbucks.starvive.category.vo.UpdateBottomCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateBottomCategoryRequestDto {

    private UUID bottomCategoryId;

    private String name;

    @Builder
    public UpdateBottomCategoryRequestDto(UUID bottomCategoryId, String name) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
    }

    public BottomCategory toCategory() {
        return BottomCategory.builder()
                .bottomCategoryId(bottomCategoryId)
                .name(name)
                .build();
    }

    public static UpdateBottomCategoryRequestDto from(UpdateBottomCategoryRequestVo updateBottomCategoryRequestVo) {
        return UpdateBottomCategoryRequestDto.builder()
                .bottomCategoryId(updateBottomCategoryRequestVo.getBottomCategoryId())
                .name(updateBottomCategoryRequestVo.getName())
                .build();
    }
}
