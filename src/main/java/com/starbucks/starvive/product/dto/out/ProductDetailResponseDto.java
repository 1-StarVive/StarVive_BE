package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductDetailImage;
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
    private UUID productOptionId;
    private String imageThumbUrl;
    private String name;
    private String optionName;
    private int price;
    private int baseDiscountRate;
    private int discountedPrice;
    private ProductStatus productStatus;
    private String productDetailContent;
    private List<ProductRequiredInfoResponseDto> requiredInfos;

    @Builder
    public ProductDetailResponseDto(UUID productId,
                                    UUID productOptionId,
                                    String imageThumbUrl,
                                    String name,
                                    String optionName,
                                    int price,
                                    int baseDiscountRate,
                                    int discountedPrice,
                                    ProductStatus productStatus,
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

    public static ProductDetailResponseDto from(Product product, ProductImage image, ProductOption option, ProductDetailImage detailImage, List<ProductRequiredInfoResponseDto> requiredInfos) {
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .productOptionId(option.getProductOptionId())
                .imageThumbUrl(image.getImageThumbUrl())
                .name(product.getName())
                .optionName(option.getName())
                .price(option.getPrice())
                .baseDiscountRate(option.getBaseDiscountRate())
                .discountedPrice(option.getDiscountedPrice())
                .productStatus(product.getProductStatus())
                .productDetailContent(detailImage.getProductDetailContent())
                .requiredInfos(requiredInfos)
                .build();
    }
}
