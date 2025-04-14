package com.starbucks.starvive.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class WishProductVo {

    private UUID wishId;             // 찜 ID
    private UUID productId;          // 상품 ID
    private String imageThumbUrl;    // 상품 썸네일 URL
    private String imageThumbAlt;    // 썸네일 ALT 텍스트
    private String productName;      // 상품명
    private int price;               // 정가 (원가)
    private int discountRate;        // 현재 적용 중인 할인율
    private int discountedPrice;     // 최종 할인가
    private int baseDiscountRate;

}
