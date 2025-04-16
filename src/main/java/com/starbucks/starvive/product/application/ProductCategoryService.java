package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.RegisterProductCategoryRequestDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductCategoryService {

    void addProductCategory(RegisterProductCategoryRequestDto registerProductCategoryRequestDto);

    List<ProductListResponseDto> getProductsByCategory(UUID topCategoryId, UUID middleCategoryId, UUID bottomCategoryId);
}
