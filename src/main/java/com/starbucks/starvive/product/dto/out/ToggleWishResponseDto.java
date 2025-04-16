package com.starbucks.starvive.product.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ToggleWishResponseDto {

    private UUID productId;
    private boolean wished;
}
