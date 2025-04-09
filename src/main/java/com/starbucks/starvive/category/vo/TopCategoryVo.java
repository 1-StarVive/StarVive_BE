package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopCategoryVo {

    private String name;

    @Builder
    public TopCategoryVo(String name) {
        this.name = name;
    }
}
