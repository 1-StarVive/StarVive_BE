package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.vo.FeaturedSectionProductVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedSectionProductResponseDto {

    private String featuredSectionId;
    private List<FeaturedSectionProductVo> products;
}
