package com.starbucks.starvive.product.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class WishStatusResponseDto {
    
    private UUID productId;
    private Boolean isWish;

    @Builder
    public WishStatusResponseDto(UUID productId, Boolean isWish) {
        this.productId = productId;
        this.isWish = isWish;
    }
} 