package com.starbucks.starvive.featuredSection.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateFeaturedSectionRequestVo {

    private UUID featuredSectionId;
    private String name;
    private boolean activated;

}