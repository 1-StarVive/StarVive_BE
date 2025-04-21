package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
public class ProductOption extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productOptionId;

    @ColumnDefault("false")
    private Integer stock; // 상품 옵션의 남은 재고 수량

    @Column(nullable = false)
    private int price;

    @ColumnDefault("false")
    private Boolean carvedAvailable; // 각인 여부

    @Column(nullable = false)
    private int baseDiscountRate;

    @Column(nullable = false)
    private int discountedPrice;

    @Column(nullable = false)
    private String name; // 길이 ? 사이즈 ?

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Builder
    public ProductOption(UUID productOptionId, Integer stock,
                         int price, Boolean carvedAvailable,
                         int baseDiscountRate, int discountedPrice, String name, UUID productId) {
        this.productOptionId = productOptionId;
        this.stock = stock;
        this.price = price;
        this.carvedAvailable = carvedAvailable;
        this.baseDiscountRate = baseDiscountRate;
        this.discountedPrice = discountedPrice;
        this.name = name;
        this.productId = productId;
    }

    public int getDiscountedPrice() {
        return (baseDiscountRate > 0)
                ? price - (price * baseDiscountRate / 100)
                : price;
    }
}
