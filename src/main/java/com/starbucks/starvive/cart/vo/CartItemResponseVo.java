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
    private UUID productOptionId;
    private String name;
    private String optionName;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private int price;
    private int baseDiscountRate;
    private int discountedPrice;
    private int quantity;
    private boolean checked;

    @Builder
    public CartItemResponseVo(UUID cartId, UUID productId,UUID productOptionId,
                              String name, String optionName, String imageThumbUrl, String imageThumbAlt,
                              int price, int baseDiscountRate, int discountedPrice, int quantity, boolean checked) {
        this.cartId = cartId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.name = name;
        this.optionName = optionName;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.price = price;
        this.baseDiscountRate = baseDiscountRate;
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
        this.checked = checked;
    }

    public static CartItemResponseVo from(Cart cart, Product product, ProductOption option, ProductImage image) {
        int price = option.getPrice();
        int baseDiscountRate = option.getBaseDiscountRate();
        int discountedPrice = (baseDiscountRate > 0) ? price - (price * baseDiscountRate / 100) : price;

        return CartItemResponseVo.builder()
                .cartId(cart.getCartId())
                .productId(cart.getProductId())
                .productOptionId(option.getProductOptionId())
                .name(product.getName())
                .optionName(option.getName())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .price(price)
                .baseDiscountRate(baseDiscountRate)
                .discountedPrice(discountedPrice)
                .quantity(cart.getQuantity())
                .checked(cart.getChecked())
                .build();
    }
}