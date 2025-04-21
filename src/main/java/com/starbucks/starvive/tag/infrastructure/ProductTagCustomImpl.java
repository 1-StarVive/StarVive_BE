package com.starbucks.starvive.tag.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.tag.domain.QProductTag;
import com.starbucks.starvive.tag.dto.out.ProductTagListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductTagCustomImpl implements ProductTagCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductTagListResponseDto> findAllTagProducts(UUID tagId) {

        QProductTag productTag = QProductTag.productTag;
        QProduct product = QProduct.product;
        QProductOption option = QProductOption.productOption;
        QProductImage image = QProductImage.productImage;

        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(tagId)
                .ifPresent(id -> builder.and(productTag.tagId.eq(id)));

        return jpaQueryFactory.select(Projections.constructor(
                        ProductTagListResponseDto.class,
                        product.productId,
                        image.imageThumbUrl,
                        image.imageThumbAlt,
                        image.isMain,
                        product.name,
                        option.baseDiscountRate,
                        option.price
                ))
                .from(product)
                .join(productTag).on(productTag.productId.eq(product.productId))
                .join(option).on(option.productId.eq(product.productId))
                .join(image).on(image.productId.eq(product.productId), image.isMain.isTrue())
                .where(builder)
                .fetch();
    }
}
