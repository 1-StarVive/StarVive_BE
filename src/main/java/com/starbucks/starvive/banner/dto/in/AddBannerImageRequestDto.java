package com.starbucks.starvive.banner.dto.in;

import com.starbucks.starvive.banner.vo.AddBannerImageRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddBannerImageRequestDto {
    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private boolean activated;

    @Builder
    public AddBannerImageRequestDto(String imageBannerAlt, String linkUrl, LocalDate postedAt, boolean activated) {
        this.imageBannerAlt = imageBannerAlt;
        this.linkUrl = linkUrl;
        this.postedAt = postedAt;
        this.activated = activated;
    }

    public static AddBannerImageRequestDto from(AddBannerImageRequestVo addBannerImageRequestVo) {
        return AddBannerImageRequestDto.builder()
                .imageBannerAlt(addBannerImageRequestVo.getImageBannerAlt())
                .linkUrl(addBannerImageRequestVo.getLinkUrl())
                .postedAt(addBannerImageRequestVo.getPostedAt())
                .activated(addBannerImageRequestVo.isActivated())
                .build();
    }
}
