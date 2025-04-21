package com.starbucks.starvive.featuredSection.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Getter
@Entity
@NoArgsConstructor
public class FeaturedSectionProduct {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID featuredSectionProductId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID featuredSectionId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productImageId;

    @Builder
    public FeaturedSectionProduct(UUID featuredSectionProductId, UUID productId,
                                  UUID featuredSectionId, UUID productOptionId, UUID productImageId) {
        this.featuredSectionProductId = featuredSectionProductId;
        this.productId = productId;
        this.featuredSectionId = featuredSectionId;
        this.productOptionId = productOptionId;
        this.productImageId = productImageId;
    }
}
