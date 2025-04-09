package com.starbucks.starvive.product.projection;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

import java.util.UUID;

public interface WishProductProjection {

    UUID getWishId();
    UUID getProductId();
    int getPrice();
    String getImageThumbUrl();
    String getImageThumbAlt();
    String getProductName();
    int getDiscountRate();
}
