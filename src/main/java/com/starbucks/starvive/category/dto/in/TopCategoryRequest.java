package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.vo.TopCategoryVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopCategoryRequest {

    private String name;

    private String thumbImageUrl;

    private String thumbAlt;

    public TopCategory toEntity() {
        return TopCategory.builder()
                .name(name)
                .thumbImageUrl(thumbImageUrl)
                .thumbAlt(thumbAlt)
                .build();
    }

    public static TopCategoryRequest from(TopCategoryVo topCategoryVo) {
        return TopCategoryRequest.builder()
                .name(topCategoryVo.getName())
                .thumbImageUrl(topCategoryVo.getThumbImageUrl())
                .thumbAlt(topCategoryVo.getThumbAlt())
                .build();
    }
}
