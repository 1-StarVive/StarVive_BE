package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductDetailImage extends BaseEntity {

   

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String productDetailContent;

    private String productId;

    @Builder
    public ProductDetailImage(String productDetailContent,
                              String productId) {
        
        this.productDetailContent = productDetailContent;
        this.productId = productId;
    }
}
