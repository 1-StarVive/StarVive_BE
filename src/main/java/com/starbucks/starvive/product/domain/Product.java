package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int baseDiscountRate;

    private boolean hasBest; // 베스트 여부
    //  베스트 상품 지정 도메인 메서드
    public void markAsBest() {
        this.hasBest = true;
    }

    //  베스트 상품 해제 도메인 메서드 (선택)
    public void unmarkAsBest() {
        this.hasBest = false;

    }

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Builder
    public Product(String name, String description,
                   int baseDiscountRate, int price,
                   boolean hasBest,
                   ProductStatus productStatus) {
        this.productId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.baseDiscountRate = baseDiscountRate;
        this.hasBest = hasBest;
       // this.price = price;
        this.productStatus = productStatus;
    }

    public void setBest(boolean b) {

    }
}
