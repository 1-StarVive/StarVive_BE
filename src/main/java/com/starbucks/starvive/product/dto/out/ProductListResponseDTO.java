package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.vo.ProductListVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDTO {

    private UUID productId;

    private String name;

    private String imageUrl;

    private String imageAlt;

    private int price;

    private int baseDiscountRate; // 할인율

    private int discountedPrice; // 할인금액


    // vo -> dto 변환
    public static ProductListResponseDTO fromProductVo(ProductListVO productListVO) {

        int price = productListVO.getProductPrice();
        int baseDiscountRate = productListVO.getProductBaseDiscountRate();
        int discountedPrice = price; // 할인율이 null 경우 -> 할인 없이 원래 가격으로 유지하기 위함

        // 할인금액 계산
        if (baseDiscountRate > 0) {
            discountedPrice = price - (price * baseDiscountRate / 100);
        }

        return ProductListResponseDTO.builder()
                .productId(productListVO.getProductId())
                .name(productListVO.getProductName())
                .imageUrl(productListVO.getMainImageUrl())
                .imageAlt(productListVO.getMainImageAlt())
                .price(price)
                .baseDiscountRate(baseDiscountRate)
                .discountedPrice(discountedPrice)
                .build();
    }
}
