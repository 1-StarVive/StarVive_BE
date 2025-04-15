package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class ProductCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productCategoryId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID topCategoryId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID middleCategoryId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID bottomCategoryId;

    @Builder
    public ProductCategory(UUID productCategoryId, UUID topCategoryId,
                           UUID middleCategoryId, UUID bottomCategoryId,
                           UUID productId) {
        this.productCategoryId = productCategoryId;
        this.topCategoryId = topCategoryId;
        this.middleCategoryId = middleCategoryId;
        this.bottomCategoryId = bottomCategoryId;
        this.productId = productId;
    }
}