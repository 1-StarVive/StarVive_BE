package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.ProductService;
import com.starbucks.starvive.product.dto.in.AddProductRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteProductRequestDto;
import com.starbucks.starvive.product.dto.in.UpdateProductRequestDto;
import com.starbucks.starvive.product.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.", tags = {"product-service"})
    @PostMapping("/add")
    public void addProduct(@RequestBody AddProductRequestVo addProductRequestVo) {
        productService.addProduct(AddProductRequestDto.from(addProductRequestVo));
    }


    @Operation(summary = "상품 단건 조회", description = "상품 ID로 하나의 상품을 조회합니다.", tags = {"product-service"})
    @GetMapping
    public ProductResponseVo getProduct(@RequestParam("productId") UUID productId) {
        return ProductResponseVo.from(productService.getProduct(productId));
    }


    @Operation(summary = "상품 전체 조회", description = "상품 목록을 조회합니다.", tags = {"product-service"})
    @GetMapping("/all")
    public List<ProductListResponseVo> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductListResponseVo::from)
                .toList();
    }

    @Operation(summary = "상품 수정", description = "상품을 수정합니다.", tags = {"product-service"})
    @PutMapping
    public void updateProduct(@RequestBody UpdateProductRequestVo updateProductRequestVo) {
        productService.updateProduct(UpdateProductRequestDto.from(updateProductRequestVo));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.", tags = {"product-service"})
    @DeleteMapping
    public void deleteProduct(@RequestBody DeleteProductRequestVo deleteProductRequestVo) {
        productService.deleteProduct(DeleteProductRequestDto.from(deleteProductRequestVo));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 정보를 조회합니다.", tags = {"product-service"})
    @GetMapping("/detail")
    public ProductDetailResponseVo getProductDetail(@RequestParam("productId") UUID productId) {
        return ProductDetailResponseVo.from(productService.getProductDetail(productId));
    }
}