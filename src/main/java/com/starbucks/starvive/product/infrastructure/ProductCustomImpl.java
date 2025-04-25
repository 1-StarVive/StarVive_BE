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

        // 상품 상세 정보 조회 (단일 결과 반환)
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
                .leftJoin(option).on(option.productId.eq(product.productId))
                .leftJoin(image).on(image.productId.eq(product.productId))
                .leftJoin(detailImage).on(detailImage.productId.eq(product.productId))
                .where(product.productId.eq(productId))
                .limit(1)  // 중복된 결과를 하나만 반환
                .fetchFirst();

        // 상품 상세 정보가 없으면 예외 처리
        if (dto == null) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        // 필수 정보 조회
        List<ProductRequiredInfoResponseDto> requiredInfos = productInfoCustom.findRequiredInfosByProductId(productId);

        // DTO에 필수 정보 추가 후 반환
        return dto.withRequiredInfos(requiredInfos);
    }
}
