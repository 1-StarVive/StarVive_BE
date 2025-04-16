package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.RegisterProductCategoryVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterProductCategoryRequestDto {

    private UUID productId;

    private UUID topCategoryId;

    private UUID middleCategoryId;

    private UUID bottomCategoryId;

    @Builder
    public RegisterProductCategoryRequestDto(UUID productId, UUID topCategoryId,
                                             UUID middleCategoryId, UUID bottomCategoryId) {
        this.productId = productId;
        this.topCategoryId = topCategoryId;
        this.middleCategoryId = middleCategoryId;
        this.bottomCategoryId = bottomCategoryId;
    }

    public static RegisterProductCategoryRequestDto from(RegisterProductCategoryVo registerProductCategoryVo) {
        return RegisterProductCategoryRequestDto.builder()
                .productId(registerProductCategoryVo.getProductId())
                .topCategoryId(registerProductCategoryVo.getTopCategoryId())
                .middleCategoryId(registerProductCategoryVo.getMiddleCategoryId())
                .bottomCategoryId(registerProductCategoryVo.getBottomCategoryId())
                .build();
    }
}
