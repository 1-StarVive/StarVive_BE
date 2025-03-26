package com.starbucks.starvive.banner.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Banner {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID bannerId;

    private String url;

    private String alt;

    private String externalUrl; // 외부 링크용

    @Column(columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID promotionId;

    @Enumerated(EnumType.STRING)
    private BannerLinkType bannerLinkType;
}
