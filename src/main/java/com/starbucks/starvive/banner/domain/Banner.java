package com.starbucks.starvive.banner.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class Banner extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
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
    
    @Builder
    public Banner(String imageBannerUrl, String imageBannerAlt, String linkUrl, 
                 LocalDate postedAt, Boolean activated) {
        this.bannerId = UUID.randomUUID();
        this.imageBannerUrl = imageBannerUrl;
        this.imageBannerAlt = imageBannerAlt;
        this.linkUrl = linkUrl;
        this.postedAt = postedAt;
        this.activated = activated;
    }
}
