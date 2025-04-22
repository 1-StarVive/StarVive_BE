package com.starbucks.starvive.banner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starbucks.starvive.banner.dto.in.UpdateBannerImageRequestDto;
import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.UUID;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate postedAt; // 배너 게시일

    @Column(nullable = false)
    private Boolean activated;  // 배너 상태

    @Column(columnDefinition = "BINARY(16)")
    private UUID promotionId;

    @Builder
    public Banner(UUID bannerId, String imageBannerUrl, String imageBannerAlt,
                  LocalDate postedAt, Boolean activated, UUID promotionId) {
        this.bannerId = bannerId;
        this.imageBannerUrl = imageBannerUrl;
        this.imageBannerAlt = imageBannerAlt;
        this.postedAt = postedAt;
        this.activated = activated;
        this.promotionId = promotionId;
    }

    public void updateImage(String newImageUrl) {
        this.imageBannerUrl = newImageUrl;
    }

    public void updateInfo(UpdateBannerImageRequestDto updateBannerImageRequestDto) {
        this.imageBannerAlt = updateBannerImageRequestDto.getImageBannerAlt();
        this.postedAt = updateBannerImageRequestDto.getPostedAt();
        this.activated = updateBannerImageRequestDto.isActivated();
    }

    public void softDelete() {
        this.activated = false;
    }
}