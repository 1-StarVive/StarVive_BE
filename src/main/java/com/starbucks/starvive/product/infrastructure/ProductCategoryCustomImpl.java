package com.starbucks.starvive.product.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductCategory;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductCategoryCustomImpl implements ProductCategoryCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductListResponseDto> findProductsByCategory(UUID topCategoryId,
                                                               UUID middleCategoryId,
                                                               UUID bottomCategoryId) {

        QProductCategory productCategory = QProductCategory.productCategory;
        QProduct product = QProduct.product;
        QProductOption option = QProductOption.productOption;
        QProductImage image = QProductImage.productImage;

        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(topCategoryId)
                .ifPresent(id -> builder.and(productCategory.topCategoryId.eq(id)));

        Optional.ofNullable(middleCategoryId)
                .ifPresent(id -> builder.and(productCategory.middleCategoryId.eq(id)));

        Optional.ofNullable(bottomCategoryId)
                .ifPresent(id -> builder.and(productCategory.bottomCategoryId.eq(id)));

        return jpaQueryFactory.select(Projections.constructor(
                        ProductListResponseDto.class,
                        product.productId,
                        image.imageThumbUrl,
                        image.imageThumbAlt,
                        product.name,
                        option.baseDiscountRate,
                        option.price
                ))
                .from(product)
                .join(productCategory).on(productCategory.productId.eq(product.productId))
                .join(option).on(option.productId.eq(product.productId))
                .join(image).on(image.productId.eq(product.productId))
                .where(builder)
                .fetch();
    }
}
