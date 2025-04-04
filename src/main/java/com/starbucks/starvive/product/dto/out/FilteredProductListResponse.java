package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.category.dto.out.MiddleWithBottomCategoryResponse;
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

    private List<MiddleWithBottomCategoryResponse> middleCategories;

    private CursorPage<ProductListResponse> products;

    @Builder
    public FilteredProductListResponse(UUID topCategoryId, String name,
                                       List<MiddleWithBottomCategoryResponse> middleCategories,
                                       CursorPage<ProductListResponse> products) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.middleCategories = middleCategories;
        this.products = products;
    }
}