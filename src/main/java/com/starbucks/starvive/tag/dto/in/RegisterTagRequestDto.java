package com.starbucks.starvive.tag.dto.in;

import com.starbucks.starvive.tag.domain.Tag;
import com.starbucks.starvive.tag.vo.RegisterTagVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterTagRequestDto {

    private String name;

    private String imageThumbUrl;

    private String imageThumbAlt;

    @Builder
    public RegisterTagRequestDto(String name, String imageThumbUrl, String imageThumbAlt) {
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
    }

    public Tag toTag() {
        return Tag.builder()
                .name(name)
                .imageThumbUrl(imageThumbUrl)
                .imageThumbAlt(imageThumbAlt)
                .build();
    }

    public static RegisterTagRequestDto from(RegisterTagVo registerTagVo, String imageThumbUrl) {
        return RegisterTagRequestDto.builder()
                .name(registerTagVo.getName())
                .imageThumbUrl(imageThumbUrl)
                .imageThumbAlt(registerTagVo.getImageThumbAlt())
                .build();
    }
}
