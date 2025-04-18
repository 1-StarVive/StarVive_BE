package com.starbucks.starvive.tag.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterTagVo {

    private UUID tagId;

    private String name;

    private String imageThumbAlt;

    @Builder
    public RegisterTagVo(UUID tagId, String name, String imageThumbAlt) {
        this.tagId = tagId;
        this.name = name;
        this.imageThumbAlt = imageThumbAlt;
    }
}
