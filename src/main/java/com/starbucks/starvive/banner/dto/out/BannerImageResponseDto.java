package com.starbucks.starvive.banner.dto.out;

import com.starbucks.starvive.banner.domain.Banner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BannerImageResponseDto {

    private  UUID bannerId;
    private  String imageBannerUrl;
    private  String imageBannerAlt;
    private  String linkUrl;
    private  LocalDate postedAt;
    private  boolean activated;

    @Builder
    public BannerImageResponseDto(UUID bannerId, String imageBannerUrl, String imageBannerAlt,
                                  String linkUrl, LocalDate postedAt, boolean activated) {
        this.bannerId = bannerId;
        this.imageBannerUrl = imageBannerUrl;
        this.imageBannerAlt = imageBannerAlt;
        this.linkUrl = linkUrl;
        this.postedAt = postedAt;
        this.activated = activated;
    }

    public static BannerImageResponseDto from(Banner banner) {
        return BannerImageResponseDto.builder()
                .bannerId(banner.getBannerId())
                .imageBannerUrl(banner.getImageBannerUrl())
                .imageBannerAlt(banner.getImageBannerAlt())
                .linkUrl(banner.getLinkUrl())
                .postedAt(banner.getPostedAt())
                .activated(banner.getActivated())
                .build();
    }
}
