package com.starbucks.starvive.banner.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddBannerImageRequestVo {

    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private boolean activated;

}
