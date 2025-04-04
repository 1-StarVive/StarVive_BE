package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class WishProductResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private int price;
    private int baseDiscountRate;
    private int discountedPrice;
    private boolean limitedEdition; // 한정판 여부
   // private boolean topProduct;     // 베스트 상픔 여부
   // private boolean newProduct;     // 신상품 여부


}
