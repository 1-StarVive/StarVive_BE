package com.starbucks.starvive.product.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class WishListRequestDto {

    private UUID userId;

    public WishListRequestDto(UUID userId) {
        this.userId = userId;
    }
}
