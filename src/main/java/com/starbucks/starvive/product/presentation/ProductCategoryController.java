package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.common.utils.CursorPage;
import com.starbucks.starvive.product.application.ProductCategoryService;
import com.starbucks.starvive.product.dto.in.RegisterProductCategoryRequestDto;
import com.starbucks.starvive.product.dto.out.ProductCategoryListResponseDto;
import com.starbucks.starvive.product.vo.RegisterProductCategoryVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/product-category")
@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Operation(summary = "카테고리별 상품 등록",
            description = "카테고리별 상품을 등록합니다.",
            tags = {"product-category-service"})
    @PostMapping
    public void addProductCategory(
            @RequestBody RegisterProductCategoryVo registerProductCategoryVo
    ) {
        productCategoryService.addProductCategory(RegisterProductCategoryRequestDto.from(registerProductCategoryVo));
    }

    @Operation(summary = "카테고리별 상품 조회",
            description = "카테고리별 상품을 조회합니다.",
            tags = {"product-category-service"})
    @GetMapping
    public CursorPage<ProductCategoryListResponseDto> getAllProductCategory(
            @RequestParam(value = "topId", required = false) UUID topCategoryId,
            @RequestParam(value ="middleId", required = false) UUID middleCategoryId,
            @RequestParam(value = "bottomId", required = false) UUID bottomCategoryId,
            @RequestParam(value = "lastProductId", required = false) UUID lastProductId,
            @RequestParam(value = "pageSize", defaultValue = "0") int pageSize
    ) {
        return productCategoryService.getProductsByCategory(topCategoryId, middleCategoryId, bottomCategoryId, lastProductId, pageSize);
    }
}
