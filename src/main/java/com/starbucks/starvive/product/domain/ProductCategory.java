package com.starbucks.starvive.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productCategoryId;

    private String topCategoryId;

    private String middleCategoryId;

    private String bottomCategoryId;

    private String productId;

    @Builder
    public ProductCategory(UUID productCategoryId, String topCategoryId,
                           String middleCategoryId, String bottomCategoryId,
                           String productId) {
        this.productCategoryId = productCategoryId;
        this.topCategoryId = topCategoryId;
        this.middleCategoryId = middleCategoryId;
        this.bottomCategoryId = bottomCategoryId;
        this.productId = productId;
    }
}
