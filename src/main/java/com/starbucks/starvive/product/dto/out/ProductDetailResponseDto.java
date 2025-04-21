package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductDetailResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String name;
    private int price;
    private ProductStatus productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseDto(UUID productId, String imageThumbUrl, String name, int price, ProductStatus productStatus,
                                    String productDetailContent, List<ProductRequiredInfoResponseDto> requiredInfos) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.name = name;
        this.price = price;
        this.productStatus = productStatus;
        this.productDetailContent = productDetailContent;
        this.requiredInfos = requiredInfos;
    }
    public static ProductDetailResponseDto from(Product product, ProductImage image, ProductOption option) {
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .imageThumbUrl(image.getImageThumbUrl())
                .name(product.getName())
                .price(option.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }
}
