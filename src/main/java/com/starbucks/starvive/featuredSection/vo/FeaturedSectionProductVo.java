package com.starbucks.starvive.featuredSection.vo;

import com.starbucks.starvive.featuredSection.Projection.FeaturedSectionProductProjection;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import jakarta.persistence.Column;
import lombok.Getter;
import java.util.UUID;

@Getter
public class FeaturedSectionProductVo {

    private final UUID featuredSectionId;
    private final UUID productId;
    private final String imageThumbUrl;
    private final String imageThumbAlt;
    private final String name;
    private final int price;
  //  private final int discountRate;
   // private final int discountedPrice;
    @Column(nullable = false)
    private final int baseDiscountRate;

    public FeaturedSectionProductVo(FeaturedSectionProductProjection featuredSectionProductProjection ) {
        this.featuredSectionId = featuredSectionProductProjection.getFeaturedSectionId();
        this.productId = featuredSectionProductProjection.getProductId();
        this.imageThumbUrl = featuredSectionProductProjection.getImageThumbUrl();
        this.imageThumbAlt = featuredSectionProductProjection.getImageThumbAlt();
        this.name = featuredSectionProductProjection.getName();
        this.price = featuredSectionProductProjection.getPrice();
       // this.discountRate = featuredSectionProductProjection.getDiscountRate();
      //  this.discountedPrice =  price * (100 - discountRate) / 100;
        this.baseDiscountRate = featuredSectionProductProjection.getBaseDiscountRate();
    }

    public FeaturedSectionProductResponseDto toItemDto() {
        return new FeaturedSectionProductResponseDto(
                productId, imageThumbUrl, imageThumbAlt, name, price,baseDiscountRate
        );
    }
}
