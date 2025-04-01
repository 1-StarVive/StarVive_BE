package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
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
public class ProductCategory extends BaseEntity {

    

    private String topCategoryId;

    private String middleCategoryId;

    private String bottomCategoryId;

    private String productId;

    @Builder
    public ProductCategory(String topCategoryId,
                           String middleCategoryId, String bottomCategoryId,
                           String productId) {
   
        this.topCategoryId = topCategoryId;
        this.middleCategoryId = middleCategoryId;
        this.bottomCategoryId = bottomCategoryId;
        this.productId = productId;
    }
}
