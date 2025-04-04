package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.common.utils.CursorPage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FilteredProductListResponse {

    private UUID topCategoryId;

    private String name;

    private CursorPage<ProductListResponse> products;

    @Builder
    public FilteredProductListResponse(UUID topCategoryId, String name,
                                       CursorPage<ProductListResponse> products) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.products = products;
    }
}