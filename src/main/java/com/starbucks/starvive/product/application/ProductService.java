package com.starbucks.starvive.product.application;

import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    public ProductListResponseDto getProductInfo(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품이 없습니다"));

        ProductOption option = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new RuntimeException("상품옵션이 없습니다" + productOptionId));

        ProductOption image = productImageRepository.findByProductIdAndIThumbnail(productId, "Y")
                .orElseThrow(() -> new RuntimeException("썸네일이 없습니다"));

        return ProductListResponseDto.builder()
                .product(product.getProductId())
                .name(product.getName())
                .price(option.getPrice())
                .thumbnailUrl(image.getImageThumbUrl())
                .isLimitedEdition(product.isLimitedEdition())
                .isNew(product.isNewProduct())
                .build();
    }




}
