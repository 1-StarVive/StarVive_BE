package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.product.domain.ProductCategory;
import com.starbucks.starvive.product.dto.in.RegisterProductCategoryRequestDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductCategoryCustomRepository;
import com.starbucks.starvive.product.infrastructure.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.DUPLICATED_PRODUCT_CATEGORY;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductCategoryCustomRepository productCategoryCustomRepository;

    @Override
    public void addProductCategory(RegisterProductCategoryRequestDto registerProductCategoryRequestDto) {

        if(!productCategoryRepository.findAllByProductId(registerProductCategoryRequestDto.getProductId()).isEmpty()) {
            throw new BaseException(DUPLICATED_PRODUCT_CATEGORY);
        }

        ProductCategory productCategory = ProductCategory.builder()
                .productId(registerProductCategoryRequestDto.getProductId())
                .topCategoryId(registerProductCategoryRequestDto.getTopCategoryId())
                .middleCategoryId(registerProductCategoryRequestDto.getMiddleCategoryId())
                .bottomCategoryId(registerProductCategoryRequestDto.getBottomCategoryId())
                .build();

        productCategoryRepository.save(productCategory);
    }

    @Override
    public List<ProductListResponseDto> getProductsByCategory(UUID topCategoryId, UUID middleCategoryId, UUID bottomCategoryId) {
        return productCategoryCustomRepository.findProductsByCategory(topCategoryId, middleCategoryId, bottomCategoryId);

    }
}