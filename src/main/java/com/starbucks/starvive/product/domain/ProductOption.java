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
public class ProductOption extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productOptionId;

    @Column(nullable = false)
    private Integer stock; // 상품 옵션의 남은 재고 수량

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Boolean carvedAvailable; // 각인 여부

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID colorId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID sizeId;

    @Builder
    public ProductOption(UUID productOptionId, Integer stock, int price,
                         UUID productId, UUID colorId, UUID sizeId,
                         Boolean carvedAvailable) {
        this.productOptionId = productOptionId;
        this.stock = stock;
        this.price = price;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.carvedAvailable = carvedAvailable;
    }

}
