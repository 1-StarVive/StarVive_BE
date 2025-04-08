package com.starbucks.starvive.cart.vo;

import com.starbucks.starvive.cart.dto.out.CartItemResponseDto;
import com.starbucks.starvive.cart.projection.CartItemListProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartItemVo {

    private final UUID userId;
    private final UUID cartId;
    private final UUID productId;
    private final String productName;
    private final String imageThumbUrl;
    private final String imageThumbAlt;
    private final int price;
    private final int quantity;

    public CartItemVo(CartItemListProjection cartItemListProjection) {
        this.userId = cartItemListProjection.getUserId();
        this.cartId = cartItemListProjection.getCartId();
        this.productId = cartItemListProjection.getProductId();
        this.productName = cartItemListProjection.getProductName();
        this.imageThumbUrl = cartItemListProjection.getImageThumbUrl();
        this.imageThumbAlt = cartItemListProjection.getImageThumbAlt();
        this.price = cartItemListProjection.getPrice();
        this.quantity = cartItemListProjection.getQuantity();
    }

    public CartItemResponseDto toResponseDto() {
        return new CartItemResponseDto(userId, cartId, productId, productName, imageThumbUrl, imageThumbAlt, price, quantity);
    }
}