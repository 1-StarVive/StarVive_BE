package com.starbucks.starvive.image.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productThumbId;

    private String imageThumbUrl;

    private String imageThumbAlt;

    private Boolean mainSelected;

    private String productId;

    @Builder
    public ProductImage(UUID productThumbId, String imageThumbUrl,
                        String imageThumbAlt, Boolean mainSelected, String productId) {
        this.productThumbId = productThumbId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.mainSelected = mainSelected;
        this.productId = productId;
    }
}
