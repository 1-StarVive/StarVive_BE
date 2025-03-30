package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId;

    private Integer remainingStock; // 상품 옵션의 남은 재고 수량

    private int price;

    private String productId;

    private String colorId;

    private String sizeId;

    private Boolean carvedAvailable; // 각인 여부

    // 작성자 : 김보미
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductOption(UUID productOptionId, Integer remainingStock, int price,
                         String productId, String colorId, String sizeId,
                         Boolean carvedAvailable) {
        this.productOptionId = productOptionId;
        this.remainingStock = remainingStock;
        this.price = price;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.carvedAvailable = carvedAvailable;
    }

}
