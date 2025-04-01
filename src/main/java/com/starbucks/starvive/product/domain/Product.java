package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Formula;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int baseDiscountRate;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Formula("BIN_TO_UUID(product_id)")
    private String productIdStr;

    @Builder
    public Product (String name, String description,
                   int baseDiscountRate, ProductStatus productStatus,
                   String productIdStr) {
        this.name = name;
        this.description = description;
        this.baseDiscountRate = baseDiscountRate;
        this.productStatus = productStatus;
        this.productIdStr = productIdStr;
    }
}
