package com.starbucks.starvive.banner.dto.out;

import com.starbucks.starvive.banner.domain.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerImageResponseDto {

    private UUID bannerId;
    private String imageBannerUrl;
    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private Boolean activated;


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
