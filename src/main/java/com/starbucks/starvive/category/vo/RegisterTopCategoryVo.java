package com.starbucks.starvive.category.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterTopCategoryVo {

    private String name;

    private String imageAlt;

    @Builder
    public RegisterTopCategoryVo(String name,
                                 String imageAlt) {
        this.name = name;
        this.imageAlt = imageAlt;
    }
}
