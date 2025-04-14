package com.starbucks.starvive.featuredSection.dto.in;

import com.starbucks.starvive.featuredSection.vo.DeleteFeaturedSectionRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteFeaturedSectionRequestDto {
    private UUID featuredSectionId;

    @Builder
    public DeleteFeaturedSectionRequestDto(UUID featuredSectionId) {
        this.featuredSectionId = featuredSectionId;
    }

    public static DeleteFeaturedSectionRequestDto from(DeleteFeaturedSectionRequestVo deleteFeaturedSectionRequestVo) {
        return DeleteFeaturedSectionRequestDto.builder()
                .featuredSectionId(deleteFeaturedSectionRequestVo.getFeaturedSectionId())
                .build();
    }
}