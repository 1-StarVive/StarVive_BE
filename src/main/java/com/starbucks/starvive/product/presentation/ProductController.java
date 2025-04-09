package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import com.starbucks.starvive.product.application.ProductService;
import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.in.ProductCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductImageCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductOptionCreateRequestDto;
import com.starbucks.starvive.product.dto.out.FilteredProductListResponse;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * ✅ 상품 생성
     * POST /products
     */
    @PostMapping("/products")
    public ResponseEntity<BaseResponseEntity<UUID>> createProduct(
            @RequestBody ProductCreateRequestDto productCreateRequestDto
    ) {
        UUID productId = productService.createProduct(productCreateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok(productId));
    }

    /**
     * ✅ 상품 옵션 생성
     * POST /products/options
     */
    @PostMapping("/products/options")
    public ResponseEntity<BaseResponseEntity<UUID>> createOption(
            @RequestBody ProductOptionCreateRequestDto productOptionCreateRequestDto
    ) {
        UUID productOptionId = productService.createProductOption(productOptionCreateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok(productOptionId));
    }

    /**
     * ✅ 상품 이미지 생성
     * POST /products/images
     */
    @PostMapping("/products/images")
    public ResponseEntity<BaseResponseEntity<UUID>> createImage(
            @RequestBody ProductImageCreateRequestDto productImageCreateRequestDto
    ) {
        UUID productImageId = productService.createProductImage(productImageCreateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok(productImageId));
    }

    /**
     * ✅ 상품 전체 조회
     * GET /products
     */
    @GetMapping
    public ResponseEntity<BaseResponseEntity<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> productList = productService.getAllProducts();
        return ResponseEntity.ok(BaseResponseEntity.ok(productList));
    }

    /**
     * ✅ 단일 상품 조회
     * GET /products/{productId}
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<BaseResponseEntity<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
        ProductResponseDto product = productService.getProduct(productId);
        return ResponseEntity.ok(BaseResponseEntity.ok(product));
    }

    /**
     * ✅ 상품 수정
     * PUT /products/{productId}
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<BaseResponseEntity<Void>> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductCreateRequestDto productCreateRequestDto
    ) {
        productService.updateProduct(productId, productCreateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    /**
     * ✅ 상품 삭제
     * DELETE /products/{productId}
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<BaseResponseEntity<Void>> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    //상품 상세 조회
    @GetMapping("/products/{productId}/detail")
    public ResponseEntity<BaseResponseEntity<ProductDetailResponseDto>> getProductDetail(
            @PathVariable UUID productId
    ) {
        ProductDetailResponseDto productDetail = productService.getProductDetail(productId);
        return ResponseEntity.ok(BaseResponseEntity.ok(productDetail));
    }

}