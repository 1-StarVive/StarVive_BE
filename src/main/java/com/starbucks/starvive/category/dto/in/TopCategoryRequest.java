package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.vo.TopCategoryVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCategoryRequest {

    private String name;

    public TopCategory toEntity() {
        return TopCategory.builder()
                .name(name)
                .build();
    }

    public static TopCategoryRequest from(TopCategoryVo topCategoryVo) {
        return TopCategoryRequest.builder()
                .name(topCategoryVo.getName())
                .build();
    }
}
