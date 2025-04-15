package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProductRequestVo {

    private String name;
    private int discountRate;
    private ProductStatus productStatus;

}
