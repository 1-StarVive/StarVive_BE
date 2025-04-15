package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateProductRequestVo {

    private UUID productId;
    private String name;
    private int discountRate;
    private ProductStatus productStatus;

}
