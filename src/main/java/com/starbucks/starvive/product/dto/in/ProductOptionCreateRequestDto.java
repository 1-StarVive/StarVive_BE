package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductOptionCreateRequestDto {

    private int price;
    private Integer stock;
    private Boolean carvedAvailable;
    private int baseDiscountRate;
    private UUID productId;

    @Builder
    public ProductOptionCreateRequestDto(int price,Integer stock, Boolean carvedAvailable, int baseDiscountRate,
                                         UUID productId) {
        this.price = price;
        this.stock = stock;
        this.carvedAvailable = carvedAvailable;
        this.baseDiscountRate = baseDiscountRate;
        this.productId = productId;
    }

        public ProductOption toEntity() {
            return ProductOption.builder()
                    .price(price)
                    .stock(stock != null ? stock : 0)
                    .carvedAvailable(carvedAvailable != null ? carvedAvailable : false)
                    .baseDiscountRate(baseDiscountRate)
                    .productId(productId)
                    .build();
    }
}
