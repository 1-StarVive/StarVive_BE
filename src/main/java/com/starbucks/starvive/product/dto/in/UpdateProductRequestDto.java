package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.domain.ProductStatus;
import com.starbucks.starvive.product.vo.UpdateProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateProductRequestDto {

    private UUID productId;
    private String name;
    private int discountRate;
    private ProductStatus productStatus;

    @Builder
    public UpdateProductRequestDto(UUID productId, String name, int discountRate, ProductStatus productStatus) {
        this.productId = productId;
        this.name = name;
        this.discountRate = discountRate;
        this.productStatus = productStatus;
    }

    public static UpdateProductRequestDto from(UpdateProductRequestVo updateProductRequestVo) {
        return UpdateProductRequestDto.builder()
                .productId(updateProductRequestVo.getProductId())
                .name(updateProductRequestVo.getName())
                .discountRate(updateProductRequestVo.getDiscountRate())
                .productStatus(updateProductRequestVo.getProductStatus())
                .build();
    }
}
