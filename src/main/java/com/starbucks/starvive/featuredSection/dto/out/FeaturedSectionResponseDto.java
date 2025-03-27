package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class FeaturedSectionResponseDto {
    private UUID featuredSectionsId;
    private String name;

    public static FeaturedSectionResponseDto from(FeaturedSection section) {
        return FeaturedSectionResponseDto.builder()
                .featuredSectionsId(section.getFeaturedSectionId())
                .name(section.getName())
                .build();
    }
}
