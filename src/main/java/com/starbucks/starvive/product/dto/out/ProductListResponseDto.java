package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductListResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private int baseDiscountRate;
    private int discountedPrice;
    private int price;

    /**
     * Builder 생성자: 할인율이 0이면 원가 그대로, 아니면 할인가 계산
     */
    @Builder
    public ProductListResponseDto(UUID productId, String imageThumbUrl, String imageThumbAlt,
                                  String name, int baseDiscountRate, int price) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.name = name;
        this.baseDiscountRate = baseDiscountRate;
        this.price = price;
        this.discountedPrice = (baseDiscountRate > 0)
                ? price - (price * baseDiscountRate / 100)
                : price;
    }

    public static ProductListResponseDto from(Product product, ProductOption option, ProductImage image) {
        return ProductListResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .baseDiscountRate(option.getBaseDiscountRate())
                .price(option.getPrice())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .build();
    }
}
