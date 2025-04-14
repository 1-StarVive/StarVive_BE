package com.starbucks.starvive.featuredSection.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FeaturedSectionProductRequestVo {

    private List<UUID> featuredSectionsIds;

}