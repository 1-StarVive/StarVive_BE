package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class ProductDetailImage extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productDetailId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String productDetailContent;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Builder
    public ProductDetailImage(String productDetailContent,
                              UUID productId) {
        
        this.productDetailContent = productDetailContent;
        this.productId = productId;
    }
}
