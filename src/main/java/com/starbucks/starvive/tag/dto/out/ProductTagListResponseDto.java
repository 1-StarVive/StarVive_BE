package com.starbucks.starvive.tag.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.out.ProductCategoryListResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductTagListResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private boolean isMain;
    private String name;
    private int baseDiscountRate;
    private int discountedPrice;
    private int price;

    @Builder
    public ProductTagListResponseDto(UUID productId, String imageThumbUrl, String imageThumbAlt, boolean isMain,
                                          String name, int baseDiscountRate, int price) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.isMain = isMain;
        this.name = name;
        this.baseDiscountRate = baseDiscountRate;
        this.price = price;
        this.discountedPrice = (baseDiscountRate > 0)
                ? price - (price * baseDiscountRate / 100)
                : price;
    }

    public static ProductCategoryListResponseDto from(Product product, ProductOption option, ProductImage image) {
        return ProductCategoryListResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .baseDiscountRate(option.getBaseDiscountRate())
                .price(option.getPrice())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .isMain(image.isMain())
                .build();
    }
}
