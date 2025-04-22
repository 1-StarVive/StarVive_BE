package com.starbucks.starvive.product.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class WishListRequestDto {

    private UUID userId;

    @Builder
    public WishListRequestDto(UUID userId) {
        this.userId = userId;
    }
}
