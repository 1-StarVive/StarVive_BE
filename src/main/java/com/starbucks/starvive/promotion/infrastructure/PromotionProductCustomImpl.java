package com.starbucks.starvive.promotion.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.promotion.domain.QPromotion;
import com.starbucks.starvive.promotion.domain.QPromotionProduct;
import com.starbucks.starvive.promotion.dto.out.PromotionProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PromotionProductCustomImpl implements PromotionProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PromotionProductResponse> findAllPromotionProducts(UUID promotionId) {

        QPromotionProduct promotionProduct = QPromotionProduct.promotionProduct;
        QPromotion promotion = QPromotion.promotion;
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QProductOption productOption = QProductOption.productOption;

        return jpaQueryFactory.select(Projections.constructor(
                        PromotionProductResponse.class,
                        product.productId,
                        product.name,
                        productImage.imageThumbUrl,
                        productImage.imageThumbAlt,
                        productOption.price,
                        productOption.baseDiscountRate
                ))
                .from(promotionProduct)
                .join(promotion).on(promotionProduct.promotionId.eq(promotion.promotionId))
                .join(product).on(promotionProduct.productId.eq(product.productId))
                .leftJoin(productImage).on(promotionProduct.productId.eq(productImage.productId))
                .leftJoin(productOption).on(promotionProduct.productId.eq(productOption.productId))
                .where(promotionProduct.promotionId.eq(promotionId), promotionProduct.deleted.isFalse())
                .fetch();
    }
}
