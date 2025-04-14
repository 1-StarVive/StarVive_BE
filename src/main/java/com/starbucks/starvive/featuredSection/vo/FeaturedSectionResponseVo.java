package com.starbucks.starvive.featuredSection.vo;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FeaturedSectionResponseVo {

    private UUID featuredSectionId;
    private String name;
    private boolean activated;

    @Builder
    public FeaturedSectionResponseVo(UUID featuredSectionId, String name, boolean activated) {
        this.featuredSectionId = featuredSectionId;
        this.name = name;
        this.activated = activated;
    }

    public static FeaturedSectionResponseVo from(FeaturedSection section) {
        return FeaturedSectionResponseVo.builder()
                .featuredSectionId(section.getFeaturedSectionId())
                .name(section.getName())
                .activated(section.isActivated())
                .build();
    }
}
