package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    private String name;

    private String code;

    private String description;

    private Double baseDiscountRate;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Builder
    public Product(UUID productId, String name, String code,
                   String description,
                   Double baseDiscountRate,
                   ProductStatus productStatus, Capacity capacity) {
        this.productId = productId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.baseDiscountRate = baseDiscountRate;
        this.productStatus = productStatus;
        this.capacity = capacity;
    }
}
