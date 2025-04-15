package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.RegisterProductCategoryRequestDto;
import com.starbucks.starvive.product.infrastructure.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public void addProductCategory(RegisterProductCategoryRequestDto registerProductCategoryRequestDto) {

    }
}
