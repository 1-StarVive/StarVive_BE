package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int baseDiscountRate;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Builder
    public Product(String name,
                   String description,
                   int baseDiscountRate,
                   ProductStatus productStatus) {
        this.name = name;
        this.description = description;
        this.baseDiscountRate = baseDiscountRate;
        this.productStatus = productStatus;
    }
}
