package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.projection.WishProductProjection;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class WishVo {

    private final UUID wishId;
    private final UUID productId;
    private final String imageThumbUrl;
    private final String imageThumbAlt;
    private final String productName;
    private final int price;
    private final int discountRate;
    private final int discountedPrice;
    private final int baseDiscountRate;

    public WishVo(WishProductProjection wishProductProjection) {
        this.wishId = wishProductProjection.getWishId();
        this.productId = wishProductProjection.getProductId();
        this.imageThumbUrl = wishProductProjection.getImageThumbUrl();
        this.imageThumbAlt = wishProductProjection.getImageThumbAlt();
        this.productName = wishProductProjection.getProductName();
        this.price = wishProductProjection.getPrice();
        this.discountRate = wishProductProjection.getDiscountRate();
        this.discountedPrice = wishProductProjection.getDiscountRate();
        this.baseDiscountRate = wishProductProjection.getDiscountRate();
    }

    public WishProductResponseDto toDto() {
        return new WishProductResponseDto(
                wishId, productId, imageThumbUrl, imageThumbAlt,productName, price,
                discountRate, discountedPrice, baseDiscountRate
        );
    }
}