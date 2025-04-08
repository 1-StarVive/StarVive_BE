package com.starbucks.starvive.product.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionCreateRequestDto {

    private int price;
    private Integer stock;
    private Boolean carvedAvailable;
    private UUID productId;
    private UUID colorId;
    private UUID sizeId;
}
