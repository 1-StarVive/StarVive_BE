package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.common.utils.CursorPage;
import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.out.ProductListResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterProductCustomRepository {

    /**
     *
     * @param request
     * @return
     */
    CursorPage<ProductListResponse> findFilteredProducts(FilterProductListRequest request);
}
