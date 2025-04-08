package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.ProductService;
import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.in.ProductCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductImageCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductOptionCreateRequestDto;
import com.starbucks.starvive.product.dto.out.FilteredProductListResponse;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return ResponseEntity.ok(productService.createProduct(productCreateRequestDto));
    }

    @PostMapping("/options")
    public ResponseEntity<UUID> createOption(@RequestBody ProductOptionCreateRequestDto productOptionCreateRequestDto) {
        return ResponseEntity.ok(productService.createProductOption(productOptionCreateRequestDto));
    }

    @PostMapping("/images")
    public ResponseEntity<UUID> createImage(@RequestBody ProductImageCreateRequestDto productImageCreateRequestDto) {
        return ResponseEntity.ok(productService.createProductImage(productImageCreateRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductCreateRequestDto productCreateRequestDto
    ) {
        productService.updateProduct(productId, productCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}