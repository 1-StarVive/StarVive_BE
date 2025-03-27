package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.product.domain.Product;
import lombok.*;

import java.util.UUID;


@Getter
@AllArgsConstructor
@Builder
public class FeaturedSectionProductResponseDto {

    private UUID featuredSectionProductListId;
    private UUID productId;

    // 상품 리스트용
    public static FeaturedSectionProductResponseDto from(FeaturedSectionProduct product) {
        return FeaturedSectionProductResponseDto.builder()
                .featuredSectionProductListId(product.getFeaturedSectionProductId())
                .productId(product.getProductId())
                .build();
    }
}
