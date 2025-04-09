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

    @Builder
    public TopCategoryResponse(UUID topCategoryId, String name) {
        this.topCategoryId = topCategoryId;
        this.name = name;
    }

    public static TopCategoryResponse from(TopCategory topCategory) {
        return TopCategoryResponse.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .build();
    }
}
