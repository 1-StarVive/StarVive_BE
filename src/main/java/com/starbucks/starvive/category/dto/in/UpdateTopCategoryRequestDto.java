package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.vo.TopCategoryRequestVo;
import com.starbucks.starvive.category.vo.UpdateTopCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateTopCategoryRequestDto {

    private UUID topCategoryId;

    private String name;

    @Builder
    public UpdateTopCategoryRequestDto(UUID topCategoryId, String name) {
        this.topCategoryId = topCategoryId;
        this.name = name;
    }

    public static UpdateTopCategoryRequestDto from(UpdateTopCategoryRequestVo updateTopCategoryRequestVo) {
        return UpdateTopCategoryRequestDto.builder()
                .topCategoryId(updateTopCategoryRequestVo.getTopCategoryId())
                .name(updateTopCategoryRequestVo.getName())
                .build();
    }
}
