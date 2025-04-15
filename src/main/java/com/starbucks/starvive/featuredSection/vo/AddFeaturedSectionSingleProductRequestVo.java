package com.starbucks.starvive.featuredSection.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddFeaturedSectionSingleProductRequestVo {

    private UUID featuredSectionId;
    private UUID productId;
    private UUID productOptionId;
    private UUID productImageId;

}
