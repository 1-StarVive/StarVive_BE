package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WishVo {

    private Wish wish;
    private Product product;
}
