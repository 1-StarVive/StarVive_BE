package com.starbucks.starvive.product.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDto {

        private String imageThumbUrl;

        private String imageThumbAlt;

        private String name;

        private int baseDiscountRate;

        private int discountedPrice;

        private int price;

        @Builder
        public ProductListResponseDto(String imageThumbUrl, String imageThumbAlt,
                                      String name, int baseDiscountRate, int price) {
            this.imageThumbUrl = imageThumbUrl;
            this.imageThumbAlt = imageThumbAlt;
            this.name = name;
            this.baseDiscountRate = baseDiscountRate;
            this.price = price;

            // 할인율이 0이면 원래 가격 그대로, 아니면 계산된 할인가
            this.discountedPrice = baseDiscountRate > 0
                    ? price - (price * baseDiscountRate / 100)
                    : price;
        }
}
