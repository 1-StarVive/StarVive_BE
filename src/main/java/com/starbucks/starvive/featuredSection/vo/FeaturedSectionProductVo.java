package com.starbucks.starvive.featuredSection.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturedSectionProductVo {

    private UUID productId;
    private String imgThumbUrl;
    private String imgThumbAlt;
    private String name;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private String code;
    private boolean limitedEdition; // 한정판 여부
    private boolean topProduct;     // 베스트 상픔 여부
    private boolean newProduct;     // 신상품 여부


}
