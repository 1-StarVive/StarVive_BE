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

    private String alt;

    private int price;

    private Double discountRate; // 할인율

    private int discountedPrice; // 할인금액

    private Boolean isNew;

    private Boolean isTop;

    private Boolean isLimitedEdition;


    // vo -> dto 변환
    public static ProductListResponseDTO fromProductVo(ProductListVO productListVO) {

        int price = productListVO.getProductPrice();
        Double discountRate = productListVO.getProductDiscountRate();
        int discountedPrice = price; // 할인율이 null 경우 -> 할인 없이 원래 가격으로 유지하기 위함

        if (discountRate != null) {
            discountedPrice = (int) (price - (price * discountRate));
        }

        return ProductListResponseDTO.builder()
                .productId(productListVO.getProductId())
                .name(productListVO.getProductName())
                .imageUrl(productListVO.getMainImageUrl())
                .alt(productListVO.getMainImageAlt())
                .price(price)
                .discountRate(discountRate)
                .discountedPrice(discountedPrice)
                .isNew(productListVO.getIsNew())
                .isTop(productListVO.getIsTop())
                .isLimitedEdition(productListVO.getIsLimitedEdition())
                .build();
    }
}
