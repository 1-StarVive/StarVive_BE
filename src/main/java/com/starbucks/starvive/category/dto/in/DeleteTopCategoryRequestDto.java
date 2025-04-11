package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.vo.DeleteTopCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteTopCategoryRequestDto {

    private UUID topCategoryId;

    @Builder
    public DeleteTopCategoryRequestDto(UUID topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public static DeleteTopCategoryRequestDto from(DeleteTopCategoryRequestVo deleteTopCategoryRequestVo) {
        return DeleteTopCategoryRequestDto.builder()
                .topCategoryId(deleteTopCategoryRequestVo.getTopCategoryId())
                .build();
    }
}
