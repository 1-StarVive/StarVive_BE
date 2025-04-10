package com.starbucks.starvive.banner.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starbucks.starvive.banner.domain.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerImageCreateRequestDto {

    private String imageBannerAlt;
    private String linkUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate postedAt;

    private Boolean activated;


    public Banner toEntity(String imageUrl) {
        return Banner.builder()
                .imageBannerUrl(imageUrl)
                .imageBannerAlt(imageBannerAlt)
                .linkUrl(linkUrl)
                .postedAt(postedAt)
                .activated(activated)
                .build();
    }
}
