package com.starbucks.starvive.featuredSection.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FeaturedSectionResponseDto {

    private UUID featuredSectionId;
    private String name;

}