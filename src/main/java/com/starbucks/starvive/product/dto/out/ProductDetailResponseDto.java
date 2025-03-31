package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

// 작성자 : 김보미

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailResponseDto {
    private UUID productId;
    private String name;
    private String thumbnailUrl;
    private String altText;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private String code;

}
