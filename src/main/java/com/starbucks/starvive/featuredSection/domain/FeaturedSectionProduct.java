package com.starbucks.starvive.featuredSection.domain;

import com.starbucks.starvive.product.domain.Product;
import jakarta.persistence.*;
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
    private UUID FeaturedSectionProductId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID featuredSectionId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productImageId;

    public FeaturedSectionProduct(UUID productId, UUID featuredSectionId, UUID productOptionId, UUID productImageId) {
        this.productId = productId;
        this.featuredSectionId = featuredSectionId;
        this.productOptionId = productOptionId;
        this.productImageId = productImageId;
    }
}
