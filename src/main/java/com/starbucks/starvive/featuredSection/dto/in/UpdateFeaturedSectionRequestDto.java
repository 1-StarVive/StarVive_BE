package com.starbucks.starvive.featuredSection.dto.in;

import com.starbucks.starvive.featuredSection.vo.UpdateFeaturedSectionRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateFeaturedSectionRequestDto {
    private UUID featuredSectionId;
    private String name;
    private boolean activated;

    @Builder
    public UpdateFeaturedSectionRequestDto(UUID featuredSectionId, String name, boolean activated) {
        this.featuredSectionId = featuredSectionId;
        this.name = name;
        this.activated = activated;
    }

    public static UpdateFeaturedSectionRequestDto from(UpdateFeaturedSectionRequestVo addFeaturedSectionRequestVo) {
        return UpdateFeaturedSectionRequestDto.builder()
                .featuredSectionId(addFeaturedSectionRequestVo.getFeaturedSectionId())
                .name(addFeaturedSectionRequestVo.getName())
                .activated(addFeaturedSectionRequestVo.isActivated())
                .build();
    }
}