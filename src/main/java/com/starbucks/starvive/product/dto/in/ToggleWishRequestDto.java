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
    private UUID productId;

    @Builder
    public ToggleWishRequestDto(UUID userId, UUID productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public static ToggleWishRequestDto from(ToggleWishRequestVo toggleWishRequestVo, UUID userId) {
        return ToggleWishRequestDto.builder()
                .userId(userId)
                .productId(toggleWishRequestVo.getProductId())
                .build();
    }
}