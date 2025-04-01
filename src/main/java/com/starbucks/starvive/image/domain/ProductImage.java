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
    private UUID id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String imageAlt;

    @Column(nullable = false)
    private UUID productId;

    @Builder
    public ProductImage(String imageUrl, String imageAlt, UUID productId) {
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
        this.productId = productId;
    }
}
