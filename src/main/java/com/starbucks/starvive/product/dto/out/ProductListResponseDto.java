package com.starbucks.starvive.product.dto.out;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.NO_EXIST_IMAGE;

@Getter
@NoArgsConstructor
public class ProductListResponseDto {

    private UUID productId;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private String name;
    private Integer stock;
    private boolean carvedAvailable;
    private int baseDiscountRate;
    private int discountedPrice;
    private int price;

    /**
     * Builder 생성자: 할인율이 0이면 원가 그대로, 아니면 할인가 계산
     */
    @Builder
    public ProductListResponseDto(UUID productId, String imageThumbUrl, String imageThumbAlt,
                                  String name,  Integer stock, boolean carvedAvailable, int baseDiscountRate, int price) {
        this.productId = productId;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.name = name;
        this.stock = stock;
        this.carvedAvailable = carvedAvailable;
        this.baseDiscountRate = baseDiscountRate;
        this.price = price;
        this.discountedPrice = (baseDiscountRate > 0)
                ? price - (price * baseDiscountRate / 100)
                : price;
    }

    public static ProductListResponseDto from(Product product, ProductOption option, ProductImage image) {
        if (image.getImageThumbUrl() == null || image.getImageThumbAlt() == null) {
            throw new BaseException(NO_EXIST_IMAGE);
        }

        return ProductListResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(option.getPrice()) // 필수값
                .stock(option.getStock() != null ? option.getStock() : 0)
                .carvedAvailable(option.getCarvedAvailable() != null && option.getCarvedAvailable())
                .imageThumbUrl(image.getImageThumbUrl())
                .imageThumbAlt(image.getImageThumbAlt())
                .build();
    }
}
