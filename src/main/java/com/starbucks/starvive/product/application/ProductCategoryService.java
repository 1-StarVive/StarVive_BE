package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.RegisterProductCategoryRequestDto;

public interface ProductCategoryService {

    void addProductCategory(RegisterProductCategoryRequestDto registerProductCategoryRequestDto);
}
