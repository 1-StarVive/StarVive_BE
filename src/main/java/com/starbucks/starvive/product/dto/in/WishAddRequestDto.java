package com.starbucks.starvive.product.dto.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
public class WishAddRequestDto {

    private UUID userId;
    private UUID productId;
}
