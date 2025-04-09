package com.starbucks.starvive.cart.projection;

import java.util.UUID;

public interface CartItemListProjection {

    UUID getUserId();
    UUID getCartId();
    UUID getProductId();
    String getProductName();
    String getImageThumbUrl();
    String getImageThumbAlt();
    int getPrice();
    int getQuantity();
}
