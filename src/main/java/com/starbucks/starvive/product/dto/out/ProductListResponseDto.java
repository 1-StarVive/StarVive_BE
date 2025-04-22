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
    private UUID productOptionId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private String optionName;
    private int baseDiscountRate;
    private int discountedPrice;
    private int price;

    /**
     * Builder 생성자: 할인율이 0이면 원가 그대로, 아니면 할인가 계산
     */
    @Builder
    public ProductListResponseDto(UUID productId, UUID productOptionId, String imageThumbUrl, String imageThumbAlt,
                                  String name, String optionName, int baseDiscountRate, int discountedPrice, int price) {
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.name = name;
        this.optionName = optionName;
        this.baseDiscountRate = baseDiscountRate;
        this.price = price;
        this.discountedPrice = (baseDiscountRate > 0)
                ? price - (price * baseDiscountRate / 100)
                : price;
    }

    public static ProductListResponseDto from(Product product, ProductOption option, ProductImage image) {
        return ProductListResponseDto.builder()
                .productId(product.getProductId())
                .productOptionId(option.getProductOptionId())
                .name(product.getName())
                .optionName(option.getName())
                .baseDiscountRate(option.getBaseDiscountRate())
                .price(option.getPrice())
                .discountedPrice(option.getDiscountedPrice())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .build();
    }
}
