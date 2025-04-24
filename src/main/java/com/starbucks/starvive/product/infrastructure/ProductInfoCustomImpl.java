package com.starbucks.starvive.product.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.product.domain.QProductRequiredInfo;
import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductInfoCustomImpl implements ProductInfoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductRequiredInfoResponseDto> findRequiredInfosByProductId(UUID productId) {
        QProductRequiredInfo info = QProductRequiredInfo.productRequiredInfo;

        return queryFactory
                .select(Projections.constructor(ProductRequiredInfoResponseDto.class,
                        info.type,
                        info.value
                ))
                .from(info)
                .where(info.productId.eq(productId))
                .fetch();
    }
}
