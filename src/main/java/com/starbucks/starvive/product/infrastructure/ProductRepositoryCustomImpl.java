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
public class ProductRepositoryCustomImpl implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QProduct product = QProduct.product;
    QProductImage productImage = QProductImage.productImage;
    QProductOption productOption = QProductOption.productOption;

    @Override
    public List<ProductListVO> findAllProducts() {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ProductListVO.class,
                        product.productId,
                        product.name,
                        productImage.imageUrl,
                        productImage.imageAlt,
                        productOption.price,
                        product.baseDiscountRate,
                        product.productIdStr,
                        product.createdAt
                ))
                .from(product)
                .join(productImage)
                .on(product.productIdStr.eq(productImage.productId))
                .join(productOption)
                .on(product.productIdStr.eq(productOption.productId))
                .fetch();
    }
}
