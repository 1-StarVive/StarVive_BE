package com.starbucks.starvive.featuredSection.dto.in;

import com.starbucks.starvive.featuredSection.vo.AddFeaturedSectionRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddFeaturedSectionRequestDto {
    private String name;
    private boolean activated;

    @Builder
    public AddFeaturedSectionRequestDto(String name, boolean activated) {
        this.name = name;
        this.activated = activated;
    }

    public static AddFeaturedSectionRequestDto from(AddFeaturedSectionRequestVo addFeaturedSectionRequestVo) {
        return AddFeaturedSectionRequestDto.builder()
                .name(addFeaturedSectionRequestVo.getName())
                .activated(addFeaturedSectionRequestVo.isActivated())
                .build();

    }
}