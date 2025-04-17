package com.starbucks.starvive.featuredSection.dto.in;

import com.starbucks.starvive.featuredSection.vo.AddFeaturedSectionSingleProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddFeaturedSectionSingleProductRequestDto {

    private UUID featuredSectionId;
    private UUID productId;
    private UUID productOptionId;
    private UUID productImageId;

    @Builder
    public AddFeaturedSectionSingleProductRequestDto(UUID featuredSectionId, UUID productId, UUID productOptionId, UUID productImageId) {
        this.featuredSectionId = featuredSectionId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.productImageId = productImageId;
    }

    public static AddFeaturedSectionSingleProductRequestDto from(AddFeaturedSectionSingleProductRequestVo addFeaturedSectionSingleProductRequestVo) {
        return AddFeaturedSectionSingleProductRequestDto.builder()
                .featuredSectionId(addFeaturedSectionSingleProductRequestVo.getFeaturedSectionId())
                .productId(addFeaturedSectionSingleProductRequestVo.getProductId())
                .productOptionId(addFeaturedSectionSingleProductRequestVo.getProductOptionId())
                .productImageId(addFeaturedSectionSingleProductRequestVo.getProductImageId())
                .build();
    }
}
