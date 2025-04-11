package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * ✅ 상품 생성
     * POST /products
     */
//    @PostMapping("/products")
//    public ResponseEntity<ErrorResponse<UUID>> createProduct(
//            @RequestBody ProductCreateRequestDto productCreateRequestDto
//    ) {
//        UUID productId = productService.createProduct(productCreateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok(productId));
//    }

    /**
     * ✅ 상품 옵션 생성
     * POST /products/options
     */
//    @PostMapping("/products/options")
//    public ResponseEntity<ErrorResponse<UUID>> createOption(
//            @RequestBody ProductOptionCreateRequestDto productOptionCreateRequestDto
//    ) {
//        UUID productOptionId = productService.createProductOption(productOptionCreateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok(productOptionId));
//    }

    /**
     * ✅ 상품 이미지 생성
     * POST /products/images
     */
//    @PostMapping("/products/images")
//    public ResponseEntity<ErrorResponse<UUID>> createImage(
//            @RequestBody ProductImageCreateRequestDto productImageCreateRequestDto
//    ) {
//        UUID productImageId = productService.createProductImage(productImageCreateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok(productImageId));
//    }

    /**
     * ✅ 상품 전체 조회
     * GET /products
     */
//    @GetMapping
//    public ResponseEntity<ErrorResponse<List<ProductResponseDto>>> getAllProducts() {
//        List<ProductResponseDto> productList = productService.getAllProducts();
//        return ResponseEntity.ok(ErrorResponse.ok(productList));
//    }

    /**
     * ✅ 단일 상품 조회
     * GET /products/{productId}
     */
//    @GetMapping("/products/{productId}")
//    public ResponseEntity<ErrorResponse<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
//        ProductResponseDto product = productService.getProduct(productId);
//        return ResponseEntity.ok(ErrorResponse.ok(product));
//    }

    /**
     * ✅ 상품 수정
     * PUT /products/{productId}
     */
//    @PutMapping("/products/{productId}")
//    public ResponseEntity<ErrorResponse<Void>> updateProduct(
//            @PathVariable UUID productId,
//            @RequestBody ProductCreateRequestDto productCreateRequestDto
//    ) {
//        productService.updateProduct(productId, productCreateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }

    /**
     * ✅ 상품 삭제
     * DELETE /products/{productId}
     */
//    @DeleteMapping("/products/{productId}")
//    public ResponseEntity<ErrorResponse<Void>> deleteProduct(@PathVariable UUID productId) {
//        productService.deleteProduct(productId);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }

    //상품 상세 조회
//    @GetMapping("/products/{productId}/detail")
//    public ResponseEntity<ErrorResponse<ProductDetailResponseDto>> getProductDetail(
//            @PathVariable UUID productId
//    ) {
//        ProductDetailResponseDto productDetail = productService.getProductDetail(productId);
//        return ResponseEntity.ok(ErrorResponse.ok(productDetail));
//    }

}