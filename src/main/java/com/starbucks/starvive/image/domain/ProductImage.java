package com.starbucks.starvive.image.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productImageId;

    @Column(nullable = false)
    private String imgThumbUrl;

    @Column(nullable = false)
    private String imgThumbAlt;

    @Column(nullable = false)
    private UUID productId;

    @Builder
    public ProductImage(UUID productImageId, String imgThumbUrl, String imgThumbAlt, UUID productId) {
        this.productImageId = productImageId;
        this.imgThumbUrl = imgThumbUrl;
        this.imgThumbAlt = imgThumbAlt;
        this.productId = productId;
    }

}
