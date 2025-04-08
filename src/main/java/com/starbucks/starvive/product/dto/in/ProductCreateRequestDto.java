package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    private String name;
    private String description;
    private int baseDiscountRate;
    private ProductStatus productStatus;

}
