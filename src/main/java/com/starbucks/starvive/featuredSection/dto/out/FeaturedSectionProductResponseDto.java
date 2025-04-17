package com.starbucks.starvive.featuredSection.dto.out;

import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FeaturedSectionProductResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private int price;
    private int baseDiscountRate;

    @Builder
    public FeaturedSectionProductResponseDto(UUID productId,
                                             String imageThumbUrl,
                                             String imageThumbAlt,
                                             String name,
                                             int price,
                                             int baseDiscountRate) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.name = name;
        this.price = price;
        this.baseDiscountRate = baseDiscountRate;
    }

    public static FeaturedSectionProductResponseDto from(
            FeaturedSectionProduct featuredSectionProduct,
            Product product,
            ProductOption option,
            ProductImage image
    ) {
        return FeaturedSectionProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(option.getPrice())
                .baseDiscountRate(option.getBaseDiscountRate())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .build();
    }
}