package com.starbucks.starvive.product.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterProductCategoryVo {

    private UUID productId;

    private UUID topCategoryId;

    private UUID middleCategoryId;

    private UUID bottomCategoryId;

    @Builder
    public RegisterProductCategoryVo(UUID productId, UUID topCategoryId,
                                     UUID middleCategoryId, UUID bottomCategoryId) {
        this.productId = productId;
        this.topCategoryId = topCategoryId;
        this.middleCategoryId = middleCategoryId;
        this.bottomCategoryId = bottomCategoryId;
    }
}
