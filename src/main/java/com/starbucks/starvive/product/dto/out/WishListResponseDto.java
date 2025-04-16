package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.domain.Wish;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class WishListResponseDto {

    private UUID wishId;
    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private int baseDiscountRate;

    @Builder
    public WishListResponseDto(UUID wishId, UUID productId, String imageThumbUrl, String imageThumbAlt,
                               String name, int price, int discountRate, int discountedPrice, int baseDiscountRate) {
        this.wishId = wishId;
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.baseDiscountRate = baseDiscountRate;
    }

    public static WishListResponseDto from(Wish wish, ProductOption option, Product product, ProductImage image) {
        int discountedPrice = option.getPrice() - (option.getPrice() * option.getBaseDiscountRate() / 100);
        return WishListResponseDto.builder()
                .wishId(wish.getWishId())
                .productId(product.getProductId())
                .name(product.getName())
                .price(option.getPrice())
                .discountRate(option.getBaseDiscountRate())
                .discountedPrice(discountedPrice)
                .baseDiscountRate(option.getBaseDiscountRate())
                .imageThumbUrl(image != null ? image.getImageThumbUrl() : null)
                .imageThumbAlt(image != null ? image.getImageThumbAlt() : null)
                .build();
    }
}
