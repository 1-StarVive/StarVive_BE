package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.projection.ProductDetailProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductDetailResponseDto {

    private UUID productId;
    private String name;
    private int price;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String productDetailContent;

    public static ProductDetailResponseDto fromProjection(ProductDetailProjection productDetailProjection) {
        return new ProductDetailResponseDto(
                productDetailProjection.getProductId(),
                productDetailProjection.getName(),
                productDetailProjection.getPrice(),
                productDetailProjection.getImageThumbUrl(),
                productDetailProjection.getImageThumbAlt(),
                productDetailProjection.getProductDetailContent()
        );
    }

}
