package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.Product;
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
    private String name;
    private ProductStatus productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseDto(UUID productId, String name, ProductStatus productStatus,
                                    String productDetailContent, List<ProductRequiredInfoResponseDto> requiredInfos) {
        this.productId = productId;
        this.name = name;
        this.productStatus = productStatus;
        this.productDetailContent = productDetailContent;
        this.requiredInfos = requiredInfos;
    }
    public static ProductDetailResponseDto from(Product product) {
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .productStatus(product.getProductStatus())
                .build();
    }
}
