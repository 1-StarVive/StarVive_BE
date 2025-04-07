package com.starbucks.starvive.product.infrastructure;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.common.utils.CursorPage;
import com.starbucks.starvive.product.dto.in.FilterProductListRequest;
import com.starbucks.starvive.product.dto.out.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FilterProductCustomImpl {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final JPAQueryFactory jpaQueryFactory;
}

