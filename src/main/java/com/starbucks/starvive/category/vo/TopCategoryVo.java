package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopCategoryVo {

    private String name;

    private String thumbImageUrl;

    private String thumbAlt;

    @Builder
    public TopCategoryVo(String name, String thumbImageUrl, String thumbAlt) {
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }
}
