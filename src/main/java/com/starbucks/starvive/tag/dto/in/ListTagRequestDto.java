package com.starbucks.starvive.tag.dto.in;

import com.starbucks.starvive.tag.domain.Tag;
import com.starbucks.starvive.tag.vo.ListTagVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListTagRequestDto {

    private String name;

    private String imageThumbUrl;

    private String imageThumbAlt;

    @Builder
    public ListTagRequestDto(String name, String imageThumbUrl, String imageThumbAlt) {
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
    }

    public Tag toTags() {
        return Tag.builder()
                .name(name)
                .imageThumbUrl(imageThumbUrl)
                .imageThumbAlt(imageThumbAlt)
                .build();
    }

    public static ListTagRequestDto from(ListTagVo listTagVo) {
        return ListTagRequestDto.builder()
                .name(listTagVo.getName())
                .imageThumbUrl(listTagVo.getImageThumbUrl())
                .imageThumbAlt(listTagVo.getImageThumbAlt())
                .build();
    }
}
