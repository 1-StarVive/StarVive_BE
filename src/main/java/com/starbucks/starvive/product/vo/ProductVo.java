package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.domain.ProductStatus;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import com.starbucks.starvive.product.projection.ProductProjection;
import lombok.Getter;
import java.util.UUID;

@Getter
public class ProductVo {

    private final UUID productId;
    private final String name;
    private final String description;
    private final int baseDiscountRate;
    private final UUID productOptionId;
    private final int price;
    private final UUID productImageId;
    private final String imageThumbUrl;
    private final String imageThumbAlt;
    private final ProductStatus productStatus;

    public ProductVo(ProductProjection p) {
        this.productId = p.getProductId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.baseDiscountRate = p.getBaseDiscountRate();
        this.productOptionId = p.getProductOptionId();
        this.price = p.getPrice();
        this.productImageId = p.getProductImageId();
        this.imageThumbUrl = p.getImageThumbUrl();
        this.imageThumbAlt = p.getImageThumbAlt();
        this.productStatus = p.getProductStatus();
    }

    public ProductResponseDto toDto() {
        return ProductResponseDto.builder()
                .productId(productId)
                .name(name)
                .description(description)
                .baseDiscountRate(baseDiscountRate)
                .productOptionId(productOptionId)
                .price(price)
                .productImageId(productImageId)
                .imageThumbUrl(imageThumbUrl)
                .imageThumbAlt(imageThumbAlt)
                .productStatus(productStatus)
                .build();
    }
}
