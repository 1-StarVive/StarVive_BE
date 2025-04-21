package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.ProductOption;
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
    private String imageThumbUrl;
    private String name;
    private int price;
    private String productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseVo(UUID productId, String imageThumbUrl, String name, int price, String productStatus,
                                   String productDetailContent, List<ProductRequiredInfoResponseDto> requiredInfos) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.name = name;
        this.price = price;
        this.productStatus = productStatus;
        this.productDetailContent = productDetailContent;
        this.requiredInfos = requiredInfos;
    }

    public static ProductDetailResponseVo from(ProductDetailResponseDto productDetailResponseDto, ProductImage image, ProductOption option) {
        return ProductDetailResponseVo.builder()
                .productId(productDetailResponseDto.getProductId())
                .imageThumbUrl(image.getImageThumbUrl())
                .name(productDetailResponseDto.getName())
                .price(option.getPrice())
                .productStatus(productDetailResponseDto.getProductStatus().name())
                .productDetailContent(productDetailResponseDto.getProductDetailContent())
                .requiredInfos(productDetailResponseDto.getRequiredInfos())
                .build();
    }
}
