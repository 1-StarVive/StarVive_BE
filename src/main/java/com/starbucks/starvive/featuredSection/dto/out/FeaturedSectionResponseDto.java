package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedSectionResponseDto {

    private String featuredSectionId;
    private String name;

    public static FeaturedSectionResponseDto from(FeaturedSection section) {
        return FeaturedSectionResponseDto.builder()
                .featuredSectionId(section.getFeaturedSectionId().toString())
                .name(section.getName())
                .build();
    }
}