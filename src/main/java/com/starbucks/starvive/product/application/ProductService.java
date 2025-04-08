package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.ProductCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductImageCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductOptionCreateRequestDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    UUID createProduct(ProductCreateRequestDto productCreateRequestDto);
    UUID createProductOption(ProductOptionCreateRequestDto productOptionCreateRequestDto);
    UUID createProductImage(ProductImageCreateRequestDto photoCreateRequestDto);
    List<ProductResponseDto> getAllProducts();
    void updateProduct(UUID productId, ProductCreateRequestDto productCreateRequestDto);
    void deleteProduct(UUID productId);
    ProductResponseDto getProduct(UUID productId);
}
