package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private UUID productId;
    private String name;
    private String description;
    private int baseDiscountRate;
    private UUID productOptionId;
    private int price;
    private UUID productImageId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private ProductStatus productStatus;

}