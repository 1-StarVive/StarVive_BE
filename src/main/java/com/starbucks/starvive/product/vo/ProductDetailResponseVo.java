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
    private UUID productOptionId;
    private String imageThumbUrl;
    private String name;
    private String optionName;
    private int price;
    private int baseDiscountRate;
    private int discountedPrice;
    private String productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseVo(UUID productId,
                                   UUID productOptionId,
                                   String imageThumbUrl,
                                   String name,
                                   String optionName,
                                   int price,
                                   int baseDiscountRate,
                                   int discountedPrice,
                                   String productStatus,
                                   String productDetailContent,
                                   List<ProductRequiredInfoResponseDto> requiredInfos) {
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.imageThumbUrl = imageThumbUrl;
        this.name = name;
        this.optionName = optionName;
        this.price = price;
        this.baseDiscountRate = baseDiscountRate;
        this.discountedPrice = discountedPrice;
        this.productStatus = productStatus;
        this.productDetailContent = productDetailContent;
        this.requiredInfos = requiredInfos;
    }

    public static ProductDetailResponseVo from(ProductDetailResponseDto productDetailResponseDto, ProductImage image, ProductOption option) {
        return ProductDetailResponseVo.builder()
          
                .productId(productDetailResponseDto.getProductId())
                .productOptionId(productDetailResponseDto.getProductOptionId())
                .imageThumbUrl(image.getImageThumbUrl())
                .name(productDetailResponseDto.getName())
                .optionName(option.getName())
                .price(option.getPrice())
                .baseDiscountRate(option.getBaseDiscountRate())
                .discountedPrice(option.getDiscountedPrice())
                .productStatus(productDetailResponseDto.getProductStatus().name())
                .productDetailContent(productDetailResponseDto.getProductDetailContent())
                .requiredInfos(productDetailResponseDto.getRequiredInfos())
                .build();
    }
}