package com.starbucks.starvive.product.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class WishProductResponseDto {

    private UUID wishId;
    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String productName;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private int baseDiscountRate;

}
