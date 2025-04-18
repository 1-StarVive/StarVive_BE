package com.starbucks.starvive.tag.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterTagVo {

    private String name;

    private String imageThumbAlt;

    @Builder
    public RegisterTagVo(String name, String imageThumbAlt) {
        this.name = name;
        this.imageThumbAlt = imageThumbAlt;
    }
}
