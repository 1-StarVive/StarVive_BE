package com.starbucks.starvive.featuredSection.dto.out;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FeaturedSectionProductResponseDto {


    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private int price;
  //  private int discountRate;
   // private int discountedPrice;
  //  @Column(nullable = false)
    private int baseDiscountRate;

}
