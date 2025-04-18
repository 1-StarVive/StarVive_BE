package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductDetailResponseVo {

    private UUID productId;
    private String name;
    private String productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseVo(UUID productId, String name, String productStatus,
                                   String productDetailContent, List<ProductRequiredInfoResponseDto> requiredInfos) {
        this.productId = productId;
        this.name = name;
        this.productStatus = productStatus;
        this.productDetailContent = productDetailContent;
        this.requiredInfos = requiredInfos;
    }

    public static ProductDetailResponseVo from(ProductDetailResponseDto productDetailResponseDto) {
        return ProductDetailResponseVo.builder()
                .productId(productDetailResponseDto.getProductId())
                .name(productDetailResponseDto.getName())
                .productStatus(productDetailResponseDto.getProductStatus().name())
                .productDetailContent(productDetailResponseDto.getProductDetailContent())
                .requiredInfos(productDetailResponseDto.getRequiredInfos())
                .build();
    }
}
