package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.TopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class TopCategoryResponse {

    private UUID topCategoryId;

    private String name;

    private String thumbImageUrl;

    private String thumbAlt;

    @Builder
    public TopCategoryResponse(UUID topCategoryId, String name,
                               String thumbImageUrl, String thumbAlt) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }

    public static TopCategoryResponse from(TopCategory topCategory) {
        return TopCategoryResponse.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .thumbImageUrl(topCategory.getThumbImageUrl())
                .thumbAlt(topCategory.getThumbAlt())
                .build();
    }
}
