package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.vo.DeleteTopCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteMiddleCategoryRequestDto {

    private UUID middleCategoryId;


    @Builder
    public DeleteMiddleCategoryRequestDto(UUID middleCategoryId) {
        this.middleCategoryId = middleCategoryId;
    }

    public MiddleCategory toEntity() {
        return MiddleCategory.builder()
                .middleCategoryId(middleCategoryId)
                .build();
    }

    public static DeleteMiddleCategoryRequestDto fromEntity(DeleteTopCategoryRequestVo updateMiddleCategoryRequestVo) {
        return DeleteMiddleCategoryRequestDto.builder()
                .middleCategoryId(updateMiddleCategoryRequestVo.getMiddleCategoryId())
                .build();
    }
}
