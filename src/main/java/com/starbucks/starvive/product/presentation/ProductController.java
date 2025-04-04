package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.out.FilteredProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    @GetMapping
    public FilteredProductListResponse getFilteredProducts(
            @ModelAttribute FilterProductListRequest request
    ) {

        return null;
    }
}