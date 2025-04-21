package com.starbucks.starvive.tag.dto.in;

import com.starbucks.starvive.tag.vo.RegisterProductTagVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterProductTagRequestDto {

    private UUID tagId;
    private List<UUID> productIds;

    @Builder
    public RegisterProductTagRequestDto(UUID tagId, List<UUID> productIds) {
        this.tagId = tagId;
        this.productIds = productIds;
    }

    public static RegisterProductTagRequestDto from(RegisterProductTagVo registerProductTagVo) {
        return RegisterProductTagRequestDto.builder()
                .tagId(registerProductTagVo.getTagId())
                .productIds(registerProductTagVo.getProductIds())
                .build();
    }
}
