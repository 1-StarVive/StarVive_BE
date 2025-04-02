package com.starbucks.starvive.featuredSection.dto.in;

import lombok.Getter;

import java.util.List;
import java.util.UUID;


@Getter
public class FeaturedSectionProductRequestDto {

    private final List<String> featuredSectionsIds;

    public FeaturedSectionProductRequestDto(List<String> featuredSectionsIds) {
        this.featuredSectionsIds = featuredSectionsIds;
    }



}