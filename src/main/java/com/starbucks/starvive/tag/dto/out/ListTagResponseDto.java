package com.starbucks.starvive.tag.dto.out;

import com.starbucks.starvive.tag.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListTagResponseDto {

    private UUID tagId;

    private String name;

    private String imageThumbUrl;

    private String imageThumbAlt;

    @Builder
    public ListTagResponseDto(UUID tagId, String name,
                              String imageThumbUrl, String imageThumbAlt) {
        this.tagId = tagId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
    }

    public static ListTagResponseDto from(Tag tag) {
        return ListTagResponseDto.builder()
                .tagId(tag.getTagId())
                .name(tag.getName())
                .imageThumbUrl(tag.getImageThumbUrl())
                .imageThumbAlt(tag.getImageThumbAlt())
                .build();
    }
}
