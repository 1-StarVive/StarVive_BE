package com.starbucks.starvive.cart.vo;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CartItemResponseVo {

    private UUID cartId;
    private UUID productId;
    private String name;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private int price;
    private int quantity;

    @Builder
    public CartItemResponseVo(UUID cartId, UUID productId,
                              String name, String imageThumbUrl, String imageThumbAlt,
                              int price, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponseVo from(Cart cart, Product product, ProductOption option, ProductImage image) {
        return CartItemResponseVo.builder()
                .cartId(cart.getCartId())
                .productId(cart.getProductId())
                .name(product.getName())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .price(option.getPrice())
                .quantity(cart.getQuantity())
                .build();
    }
}