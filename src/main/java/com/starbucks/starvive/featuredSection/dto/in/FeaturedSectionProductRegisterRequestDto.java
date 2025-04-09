package com.starbucks.starvive.featuredSection.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedSectionProductRegisterRequestDto {

    private UUID featuredSectionId;
    private List<FeaturedSectionProductItemDto> products;

}
