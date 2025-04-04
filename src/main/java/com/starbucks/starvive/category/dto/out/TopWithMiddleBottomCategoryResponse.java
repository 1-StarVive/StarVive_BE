package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.TopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class TopWithMiddleBottomCategoryResponse {

    private UUID topCategoryId;

    private String name;

    private String thumbImageUrl;

    private String thumbAlt;

    private List<MiddleWithBottomCategoryResponse> middleCategories;

    @Builder
    public TopWithMiddleBottomCategoryResponse(UUID topCategoryId, String name,
                                               String thumbImageUrl, String thumbAlt,
                                               List<MiddleWithBottomCategoryResponse> middleCategories) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
        this.middleCategories = middleCategories;
    }

    public static TopWithMiddleBottomCategoryResponse from(TopCategory topCategory,
                                                           List<MiddleWithBottomCategoryResponse> middleCategories) {
        return TopWithMiddleBottomCategoryResponse.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .thumbImageUrl(topCategory.getThumbImageUrl())
                .thumbAlt(topCategory.getThumbAlt())
                .middleCategories(middleCategories)
                .build();
    }
}
