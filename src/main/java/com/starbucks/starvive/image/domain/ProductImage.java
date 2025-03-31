package com.starbucks.starvive.image.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID productThumbId;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String imageAlt;

    private String productId;

    @Builder
    public ProductImage(UUID productThumbId, String imageUrl,
                        String imageAlt, String productId) {
        this.productThumbId = productThumbId;
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
        this.productId = productId;
    }
}
