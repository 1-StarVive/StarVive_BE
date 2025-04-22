package com.starbucks.starvive.product.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.common.utils.CursorPage;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductCategory;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.product.dto.out.ProductCategoryListResponseDto;
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
    public CursorPage<ProductCategoryListResponseDto> findProductsByCategory(UUID topCategoryId,
                                                               UUID middleCategoryId,
                                                               UUID bottomCategoryId,
                                                               UUID lastProductId,
                                                               int pageSize) {

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

        Optional.ofNullable(lastProductId)
                .ifPresent(cursor -> builder.and(product.productId.lt(cursor)));

        List<ProductCategoryListResponseDto> results = jpaQueryFactory.select(Projections.constructor(
                        ProductCategoryListResponseDto.class,
                        product.productId,
                        image.imageThumbUrl,
                        image.imageThumbAlt,
                        image.isMain,
                        product.name,
                        option.baseDiscountRate,
                        option.price
                ))
                .from(product)
                .join(productCategory).on(productCategory.productId.eq(product.productId))
                .join(option).on(option.productId.eq(product.productId))
                .join(image).on(image.productId.eq(product.productId), image.isMain.isTrue())
                .where(builder)
                .orderBy(product.productId.desc())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = results.size() > pageSize;
        UUID nextCursor = hasNext ? results.get(pageSize).getProductId() : null;

        List<ProductCategoryListResponseDto> content = hasNext ? results.subList(0, pageSize) : results;

        return CursorPage.<ProductCategoryListResponseDto> builder()
                .content(content)
                .nextCursor(nextCursor != null ? nextCursor.toString() : null)
                .hasNext(hasNext)
                .pageSize(pageSize)
                .build();
    }
}
