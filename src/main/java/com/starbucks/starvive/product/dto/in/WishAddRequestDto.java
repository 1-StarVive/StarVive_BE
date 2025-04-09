package com.starbucks.starvive.product.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishAddRequestDto {

    private UUID userId;
    private UUID productId;
    private UUID productOptionId;
}
