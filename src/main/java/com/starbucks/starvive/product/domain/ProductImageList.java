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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductImageId;

    @Column(nullable = false)
    private Boolean mainSelected;

    @Builder
    public ProductImageList(Long productImageId, Boolean mainSelected) {
        ProductImageId = productImageId;
        this.mainSelected = mainSelected;
    }
}
