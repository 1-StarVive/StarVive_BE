package com.starbucks.starvive.banner.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BannerImageVo {

    private UUID bannerId;
    private String imageBannerUrl;
    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private Boolean activated;

}