package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.AddProductRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteProductRequestDto;
import com.starbucks.starvive.product.dto.in.UpdateProductRequestDto;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import com.starbucks.starvive.product.dto.BestProductResponseDto;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    void addProduct(AddProductRequestDto addProductRequestDto);
    void updateProduct(UpdateProductRequestDto updateProductRequestDto);
    void deleteProduct(DeleteProductRequestDto deleteProductRequestDto);
    ProductResponseDto getProduct(UUID productId);
    List<ProductListResponseDto> getProductsByCursor(UUID lastProductId, int size);
    ProductDetailResponseDto getProductDetail(UUID productId);
    List<BestProductResponseDto> getBestProducts();

}
