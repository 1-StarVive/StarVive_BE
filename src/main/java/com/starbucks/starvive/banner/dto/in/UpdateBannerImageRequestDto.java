package com.starbucks.starvive.banner.dto.in;

import com.starbucks.starvive.banner.vo.UpdateBannerImageRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateBannerImageRequestDto {
    private UUID bannerId;
    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private boolean activated;

    @Builder
    public UpdateBannerImageRequestDto(UUID bannerId, String imageBannerAlt, String linkUrl, LocalDate postedAt, boolean activated) {
        this.bannerId = bannerId;
        this.imageBannerAlt = imageBannerAlt;
        this.linkUrl = linkUrl;
        this.postedAt = postedAt;
        this.activated = activated;
    }

    public static UpdateBannerImageRequestDto from(UpdateBannerImageRequestVo vo) {
        return UpdateBannerImageRequestDto.builder()
                .bannerId(vo.getBannerId())
                .imageBannerAlt(vo.getImageBannerAlt())
                .linkUrl(vo.getLinkUrl())
                .postedAt(vo.getPostedAt())
                .activated(vo.isActivated())
                .build();
    }
}