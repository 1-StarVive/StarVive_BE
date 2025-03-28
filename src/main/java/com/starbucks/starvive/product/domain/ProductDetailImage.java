package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductDetailImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productDetailId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String productDetailContent;

    private Integer order;

    private String productId;

    @Builder
    public ProductDetailImage(UUID productDetailId, String productDetailContent,
                              Integer order, String productId) {
        this.productDetailId = productDetailId;
        this.productDetailContent = productDetailContent;
        this.order = order;
        this.productId = productId;
    }
}
