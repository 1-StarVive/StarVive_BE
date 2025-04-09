package com.starbucks.starvive.featuredSection.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FeaturedSectionProductGroupResponseDto {

    private UUID featuredSectionsId;
    private List<FeaturedSectionProductResponseDto> products;
}
