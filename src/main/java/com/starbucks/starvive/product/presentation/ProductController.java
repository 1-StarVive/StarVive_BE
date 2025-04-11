package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.ProductService;

import com.starbucks.starvive.product.dto.in.ProductCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductImageCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductOptionCreateRequestDto;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

//    @PostMapping("/product")
//    public BaseResponseEntity<UUID> createProduct(
//            @RequestBody ProductCreateRequestDto productCreateRequestDto
//    ) {
//        UUID productId = productService.createProduct(productCreateRequestDto);
//        return BaseResponseEntity.ok(productId);
//    }
//
//    @PostMapping("/product/option")
//    public BaseResponseEntity<UUID> createOption(
//            @RequestBody ProductOptionCreateRequestDto productOptionCreateRequestDto
//    ) {
//        UUID productOptionId = productService.createProductOption(productOptionCreateRequestDto);
//        return BaseResponseEntity.ok(productOptionId);
//    }
//
//    @PostMapping("/product/image")
//    public BaseResponseEntity<UUID> createImage(
//            @RequestBody ProductImageCreateRequestDto productImageCreateRequestDto
//    ) {
//        UUID productImageId = productService.createProductImage(productImageCreateRequestDto);
//        return BaseResponseEntity.ok(productImageId);
//    }
//
//    @GetMapping("/products")
//    public BaseResponseEntity<List<ProductResponseDto>> getAllProducts() {
//        List<ProductResponseDto> productList = productService.getAllProducts();
//        return BaseResponseEntity.ok(productList);
//    }
//
//    @GetMapping("/product/{productId}")
//    public BaseResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID productId) {
//        ProductResponseDto product = productService.getProduct(productId);
//        return BaseResponseEntity.ok(product);
//    }
//
//    @PutMapping("/product/{productId}")
//    public BaseResponseEntity<Void> updateProduct(
//            @PathVariable UUID productId,
//            @RequestBody ProductCreateRequestDto productCreateRequestDto
//    ) {
//        productService.updateProduct(productId, productCreateRequestDto);
//        return BaseResponseEntity.ok();
//    }
//
//    @DeleteMapping("/product/{productId}")
//    public BaseResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
//        productService.deleteProduct(productId);
//        return BaseResponseEntity.ok();
//    }
//
//    @GetMapping("/product/{productId}/detail")
//    public BaseResponseEntity<ProductDetailResponseDto> getProductDetail(
//            @PathVariable UUID productId
//    ) {
//        ProductDetailResponseDto productDetail = productService.getProductDetail(productId);
//        return BaseResponseEntity.ok(productDetail);
//    }
}