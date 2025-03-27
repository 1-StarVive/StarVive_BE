package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId;

    private Integer remainingStock; // 상품 옵션의 남은 재고 수량

    private int price;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID colorId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID sizeId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID carvingId;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private Boolean isEngravedAvailable;

    private Boolean hasColor;

    private Boolean hasSize;
}
