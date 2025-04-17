package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FeaturedSectionResponseDto {

    private UUID featuredSectionId;
    private String name;
    private boolean activated;

    @Builder
    public FeaturedSectionResponseDto(UUID featuredSectionId, String name, boolean activated) {
        this.featuredSectionId = featuredSectionId;
        this.name = name;
        this.activated = activated;
    }

    public static FeaturedSectionResponseDto from(FeaturedSection featuredSection) {
        return FeaturedSectionResponseDto.builder()
                .featuredSectionId(featuredSection.getFeaturedSectionId())
                .name(featuredSection.getName())
                .activated(featuredSection.isActivated())
                .build();
    }
}