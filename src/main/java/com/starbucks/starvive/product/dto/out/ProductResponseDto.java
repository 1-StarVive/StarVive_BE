package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private UUID productId;
    private String name;
    private ProductStatus productStatus;

    @Builder
    public ProductResponseDto(UUID productId, String name, ProductStatus productStatus) {
        this.productId = productId;
        this.name = name;
        this.productStatus = productStatus;
    }

    public static ProductResponseDto from(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .productStatus(product.getProductStatus())
                .build();
    }
}