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

    BooleanBuilder builder = new BooleanBuilder();

    StringTemplate imageIdBinary = Expressions.stringTemplate(
            "BIN_TO_UUID({0})",
            product.productId
    );

    StringTemplate optionIdBinary = Expressions.stringTemplate(
            "BIN_TO_UUID({0})",
            product.productId
    );

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
                        product.baseDiscountRate
                ))
                .from(product)
                .join(productImage)
                .on(imageIdBinary.eq(productImage.productId))
                //.on(productImage.productId.eq(imageIdBinary))
                .join(productOption)
                .on(optionIdBinary.eq(productOption.productId))
                //.on(productOption.productId.eq(optionIdBinary))
                .where(builder)
                .fetch();
    }
}
