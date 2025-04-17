package com.starbucks.starvive.banner.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddBannerImageRequestVo {

    private String imageBannerAlt;
    private LocalDate postedAt;
    private boolean activated;

}
