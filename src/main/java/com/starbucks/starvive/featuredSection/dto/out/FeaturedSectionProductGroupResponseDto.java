package com.starbucks.starvive.featuredSection.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FeaturedSectionProductGroupResponseDto {

    private UUID featuredSectionsId;
    private List<FeaturedSectionProductResponseDto> products;

    @Builder
    public FeaturedSectionProductGroupResponseDto(UUID featuredSectionsId,
                                                  List<FeaturedSectionProductResponseDto> products) {
        this.featuredSectionsId = featuredSectionsId;
        this.products = products;
    }
}
