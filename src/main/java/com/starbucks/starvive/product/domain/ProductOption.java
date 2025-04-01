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
public class ProductOption extends BaseEntity {

    @Column(nullable = false)
    private Integer stock; // 상품 옵션의 남은 재고 수량

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Boolean carvedAvailable; // 각인 여부

    private String productId;

    private String colorId;

    private String sizeId;

    // 작성자 : 김보미
    //@ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "product_id")
   // private Product product;


    @Builder
    public ProductOption(Integer stock, int price,
                         String productId, String colorId, String sizeId,
                         Boolean carvedAvailable) {
        this.stock = stock;
        this.price = price;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.carvedAvailable = carvedAvailable;
    }

}
