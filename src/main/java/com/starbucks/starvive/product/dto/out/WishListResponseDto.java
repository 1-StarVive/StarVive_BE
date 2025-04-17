package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class WishListResponseDto {

    private UUID productId;
    private String name;
    private String imageThumbUrl;
    private String imageThumbAlt;

    @Builder
    public WishListResponseDto(UUID productId, String name, String imageThumbUrl, String imageThumbAlt) {
        this.productId = productId;
        this.name = name;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
    }

    public static WishListResponseDto fromProduct(Product product, ProductImage image) {
        return WishListResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .imageThumbUrl(image != null ? image.getImageThumbUrl() : null)
                .imageThumbAlt(image != null ? image.getImageThumbAlt() : null)
                .build();
    }
}
