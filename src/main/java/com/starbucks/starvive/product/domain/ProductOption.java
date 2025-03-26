package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class ProductOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId;

    private Integer remainingStock;

    private Integer price;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID colorId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID sizeId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID carvingId;

    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;
}
