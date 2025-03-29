package com.starbucks.starvive.banner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Banner {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID bannerId;

    @Column(nullable = false)
    private String imageBannerUrl;

    @Column(nullable = false)
    private String imageBannerAlt;

    @Column(nullable = false)
    private String linkUrl; // 이동 주소

    @Column(nullable = false)
    private LocalDate postedAt; // 배너 게시일

    @Column(nullable = false)
    private Boolean activated;  // 배너 상태
}
