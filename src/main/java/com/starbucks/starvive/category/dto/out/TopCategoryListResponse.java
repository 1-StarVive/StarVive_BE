package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.TopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class TopCategoryListResponse {

    private UUID topCategoryId;

    private String name;

    @Builder
    public TopCategoryListResponse(UUID topCategoryId, String name) {
        this.topCategoryId = topCategoryId;
        this.name = name;
    }

    public static TopCategoryListResponse from(TopCategory topCategory) {
        return TopCategoryListResponse.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .build();
    }
}

