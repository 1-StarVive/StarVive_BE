package com.starbucks.starvive.featuredSection.dto.in;

import com.starbucks.starvive.featuredSection.vo.AddFeaturedSectionProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddFeaturedSectionProductRequestDto {

    private UUID featuredSectionId;
    private List<UUID> productIds;

    @Builder
    public AddFeaturedSectionProductRequestDto(UUID featuredSectionId, List<UUID> productIds) {
        this.featuredSectionId = featuredSectionId;
        this.productIds = productIds;
    }

    public static AddFeaturedSectionProductRequestDto from(AddFeaturedSectionProductRequestVo addFeaturedSectionProductRequestVo) {
        return AddFeaturedSectionProductRequestDto.builder()
                .featuredSectionId(addFeaturedSectionProductRequestVo.getFeaturedSectionId())
                .productIds(addFeaturedSectionProductRequestVo.getProductIds())
                .build();
    }
}