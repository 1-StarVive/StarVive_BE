package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.ToggleWishRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ToggleWishRequestDto {
    private UUID userId;
    private UUID productOptionId;

    @Builder
    public ToggleWishRequestDto(UUID userId, UUID productOptionId) {
        this.userId = userId;
        this.productOptionId = productOptionId;
    }

    public static ToggleWishRequestDto from(ToggleWishRequestVo toggleWishRequestVo) {
        return ToggleWishRequestDto.builder()
                .userId(toggleWishRequestVo.getUserId())
                .productOptionId(toggleWishRequestVo.getProductOptionId())
                .build();
    }
}