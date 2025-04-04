package com.starbucks.starvive.product.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.product.vo.ProductListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl {


}
