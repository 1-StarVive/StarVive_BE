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
    private UUID id;
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "featuredSectionId")
    private FeaturedSection featuredSection;
}
