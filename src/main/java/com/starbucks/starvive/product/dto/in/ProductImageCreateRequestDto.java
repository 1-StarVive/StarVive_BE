package com.starbucks.starvive.product.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageCreateRequestDto {
    private String imageThumbUrl;
    private String imageThumbAlt;
    private UUID productId;
}
