package com.starbucks.starvive.featuredSection.Projection;

import java.util.UUID;

public interface FeaturedSectionProductProjection {

    UUID getFeaturedSectionId();
    UUID getProductId();
    String getImageThumbUrl();
    String getImageThumbAlt();
    String getName();
    int getPrice();
  //  int getDiscountRate();
    int getBaseDiscountRate();
    //int getDiscountedPrice();
   // int getBaseDiscountRate();
}
