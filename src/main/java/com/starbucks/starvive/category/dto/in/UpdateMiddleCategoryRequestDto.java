package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.vo.UpdateMiddleCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateMiddleCategoryRequestDto {

    private UUID middleCategoryId;

    private String name;

    @Builder
    public UpdateMiddleCategoryRequestDto(UUID middleCategoryId, String name) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
    }

    public MiddleCategory toEntity() {
        return MiddleCategory.builder()
                .middleCategoryId(middleCategoryId)
                .name(name)
                .build();
    }

    public static UpdateMiddleCategoryRequestDto fromEntity(UpdateMiddleCategoryRequestVo updateMiddleCategoryRequestVo) {
        return UpdateMiddleCategoryRequestDto.builder()
                .middleCategoryId(updateMiddleCategoryRequestVo.getMiddleCategoryId())
                .name(updateMiddleCategoryRequestVo.getName())
                .build();
    }
}
