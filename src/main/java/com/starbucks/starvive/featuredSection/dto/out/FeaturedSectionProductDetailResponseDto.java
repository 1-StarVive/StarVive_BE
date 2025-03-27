package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Builder
public class FeaturedSectionProductDetailResponseDto {

    private UUID featuredSectionId;
    private String featuredSectionName;
    private UUID featuredSectionProductId;
    private UUID productId;

    // 상세 조회용
    public static FeaturedSectionProductDetailResponseDto from(FeaturedSectionProduct product) {
        return FeaturedSectionProductDetailResponseDto.builder()
                .featuredSectionId(product.getFeaturedSection().getFeaturedSectionId())
                .featuredSectionName(product.getFeaturedSection().getFeaturedSectionName())
                .featuredSectionProductId(product.getFeaturedSectionProductId())
                .productId(product.getProductId())
                .build();
    }

}
