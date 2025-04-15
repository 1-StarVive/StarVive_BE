package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.in.AddProductRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteProductRequestDto;
import com.starbucks.starvive.product.dto.in.UpdateProductRequestDto;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public void addProduct(AddProductRequestDto addProductRequestDto) {
        Product product = Product.builder()
                .name(addProductRequestDto.getName())
                .productStatus(addProductRequestDto.getProductStatus())
                .build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findByProductId(updateProductRequestDto.getProductId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));
        product.update(updateProductRequestDto.getName(), updateProductRequestDto.getProductStatus());
    }

    @Override
    public void deleteProduct(DeleteProductRequestDto deleteProductRequestDto) {
        Product product = productRepository.findByProductId(deleteProductRequestDto.getProductId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto getProduct(UUID productId) {
        return ProductResponseDto.from(productRepository.findByProductId(productId)
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductListResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    ProductOption option = productOptionRepository.findFirstByProductId(product.getProductId())
                            .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));

                    ProductImage image = productImageRepository.findFirstByProductId(product.getProductId())
                            .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

                    return ProductListResponseDto.from(product, option, image);
                })
                .collect(Collectors.toList());
    }

        @Override
    public ProductDetailResponseDto getProductDetail(UUID productId) {
        return ProductDetailResponseDto.from(productRepository.findByProductId(productId)
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT)));
    }
}

