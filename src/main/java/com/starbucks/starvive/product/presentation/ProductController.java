package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.ProductService;
import com.starbucks.starvive.product.dto.out.ProductListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


//    @Operation(summary = "Product All List",
//            description = "Product All List API 입니다",
//            tags = {"Product-Service"})
//    @GetMapping
//    public List<ProductListResponseDTO> getProductAllList() {
//        return productService.getAllProducts();
//    }
}
