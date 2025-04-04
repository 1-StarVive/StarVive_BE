package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int baseDiscountRate;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Builder
    public Product(UUID productId, String name, String description,
                   int baseDiscountRate, ProductStatus productStatus) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.baseDiscountRate = baseDiscountRate;
        this.productStatus = productStatus;
    }
}
