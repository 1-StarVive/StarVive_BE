package com.starbucks.starvive.banner.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BannerImageUpdateRequestDto {

    private String imageBannerAlt;
    private String linkUrl;
    private LocalDate postedAt;
    private Boolean activated;

}
