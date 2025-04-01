package com.starbucks.starvive.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListVO {

    private UUID productId;

    private String productName;

    private String mainImageUrl;

    private String mainImageAlt;

    private int productPrice;

    private int productBaseDiscountRate;
}
