package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class FeaturedSectionResponseDto {
    private UUID featuredSectionId;
    private String name;
    private boolean activated;

    public static FeaturedSectionResponseDto from(FeaturedSection featuredSection) {
        return FeaturedSectionResponseDto.builder()
                .featuredSectionId(featuredSection.getFeaturedSectionId())
                .name(featuredSection.getName())
                .activated(featuredSection.isActivated())
                .build();
    }
}