package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.vo.FeaturedSectionProductVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FeaturedSectionResponseDto {
    private String featuredSectionsId;
    private String name;

}