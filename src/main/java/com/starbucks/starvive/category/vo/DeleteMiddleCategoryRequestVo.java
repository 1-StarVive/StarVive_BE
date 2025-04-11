package com.starbucks.starvive.category.vo;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.dto.in.DeleteMiddleCategoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteMiddleCategoryRequestVo {

    private UUID middleCategoryId;

    @Builder
    public DeleteMiddleCategoryRequestVo(UUID middleCategoryId) {
        this.middleCategoryId = middleCategoryId;
    }

    public MiddleCategory toEntity() {
        return MiddleCategory.builder()
                .middleCategoryId(middleCategoryId)
                .build();
    }

    public static DeleteMiddleCategoryRequestDto fromEntity(DeleteMiddleCategoryRequestVo deleteMiddleCategoryRequestVo) {
        return DeleteMiddleCategoryRequestDto.builder()
                .middleCategoryId(deleteMiddleCategoryRequestVo.getMiddleCategoryId())
                .build();
    }
}
