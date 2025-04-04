package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.utils.CursorPage;
import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.out.ProductListResponse;

public interface ProductService {

    CursorPage<ProductListResponse> findFilteredProducts(FilterProductListRequest request);

}
