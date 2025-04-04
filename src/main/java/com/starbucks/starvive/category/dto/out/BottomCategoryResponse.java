package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.BottomCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class BottomCategoryResponse {

    private UUID bottomCategoryId;

    private String name;

    @Builder
    public BottomCategoryResponse(UUID bottomCategoryId,
                                  String name){
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
    }

    public static BottomCategoryResponse from(BottomCategory bottomCategory) {
        return BottomCategoryResponse.builder()
                .bottomCategoryId(bottomCategory.getBottomCategoryId())
                .name(bottomCategory.getName())
                .build();
    }
}
