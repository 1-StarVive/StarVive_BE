package com.starbucks.starvive.product.projection;

import com.starbucks.starvive.product.domain.ProductStatus;
import java.util.UUID;

public interface ProductProjection {

    UUID getProductId();
    String getName();
    String getDescription();
    int getBaseDiscountRate();
    UUID getProductOptionId();
    int getPrice();
    UUID getProductImageId();
    String getImageThumbUrl();
    String getImageThumbAlt();
    ProductStatus getProductStatus();
}
