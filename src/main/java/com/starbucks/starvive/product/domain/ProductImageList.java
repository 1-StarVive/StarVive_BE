package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductImageList extends BaseEntity {

    
    @Column(nullable = false)
    private Boolean mainSelected;

    @Builder
    public ProductImageList(Boolean mainSelected) {
        
        this.mainSelected = mainSelected;
    }
}
