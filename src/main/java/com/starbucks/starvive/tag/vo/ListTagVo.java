package com.starbucks.starvive.tag.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListTagVo {

    private UUID tagId;

    private String name;

    private String imageThumbUrl;

    private String imageThumbAlt;

    @Builder
    public ListTagVo(UUID tagId, String name,
                     String imageThumbUrl, String imageThumbAlt) {
        this.tagId = tagId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
    }
}
