package com.starbucks.starvive.product.projection;

import java.util.UUID;

public interface ProductDetailProjection {

    UUID getProductId();
    String getName();
    int getPrice();
    String getImageThumbUrl();
    String getImageThumbAlt();
    String getProductDetailContent();
}
