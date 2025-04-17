package com.starbucks.starvive.image.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
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
    private String imageThumbUrl;

    @Column(nullable = false)
    private String imageThumbAlt;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private boolean isMain;

    @Builder
    public ProductImage(UUID productImageId, String imageThumbUrl, String imageThumbAlt, UUID productId
    , boolean isMain) {
        this.productImageId = productImageId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.productId = productId;
        this.isMain = isMain;
    }
}
