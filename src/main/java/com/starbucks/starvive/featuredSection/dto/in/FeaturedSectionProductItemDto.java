package com.starbucks.starvive.featuredSection.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedSectionProductItemDto {

    private UUID productId;
    private UUID productOptionId;
    private UUID productImageId;

}
