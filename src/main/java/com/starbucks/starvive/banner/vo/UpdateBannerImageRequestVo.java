package com.starbucks.starvive.banner.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateBannerImageRequestVo {

    private UUID bannerId;
    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private boolean activated;
}
