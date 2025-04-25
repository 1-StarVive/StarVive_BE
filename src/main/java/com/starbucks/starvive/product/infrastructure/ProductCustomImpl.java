package com.starbucks.starvive.product.infrastructure;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.QProductImage;
import com.starbucks.starvive.product.domain.QProduct;
import com.starbucks.starvive.product.domain.QProductDetailImage;
import com.starbucks.starvive.product.domain.QProductOption;
import com.starbucks.starvive.product.domain.QProductRequiredInfo;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductCustomImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final ProductInfoCustom productInfoCustom;

    @Override
    public ProductDetailResponseDto findProductDetailById(UUID productId) {
        QProduct product = QProduct.product;
        QProductOption option = QProductOption.productOption;
        QProductImage image = QProductImage.productImage;
        QProductDetailImage detailImage = QProductDetailImage.productDetailImage;

        // 1. 기본 상품 상세 정보 조회
        ProductDetailResponseDto dto = queryFactory
                .select(Projections.constructor(ProductDetailResponseDto.class,
                        product.productId,
                        option.productOptionId,
                        image.imageThumbUrl,
                        product.name,
                        option.name,
                        option.price,
                        option.baseDiscountRate,
                        option.discountedPrice,
                        product.productStatus,
                        detailImage.productDetailContent
                ))
                .from(product)
                .join(option).on(option.productId.eq(product.productId))
                .join(image).on(image.productId.eq(product.productId))
                .join(detailImage).on(detailImage.productId.eq(product.productId))
                .where(product.productId.eq(productId))
                .fetchOne();

        if (dto == null) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        List<ProductRequiredInfoResponseDto> requiredInfos =
                productInfoCustom.findRequiredInfosByProductId(productId);

        return dto.withRequiredInfos(requiredInfos);
    }

}
