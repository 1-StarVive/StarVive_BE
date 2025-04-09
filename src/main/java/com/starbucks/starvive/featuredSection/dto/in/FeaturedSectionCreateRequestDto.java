package com.starbucks.starvive.featuredSection.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedSectionCreateRequestDto {

    private String name;
    private boolean activated;

}
