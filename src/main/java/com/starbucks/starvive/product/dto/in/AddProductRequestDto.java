package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.domain.ProductStatus;
import com.starbucks.starvive.product.vo.AddProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProductRequestDto {

    private String name;
    private ProductStatus productStatus;

    @Builder
    public AddProductRequestDto(String name, ProductStatus productStatus) {
        this.name = name;
        this.productStatus = productStatus;
    }

    public static AddProductRequestDto from(AddProductRequestVo addProductRequestVo) {
        return AddProductRequestDto.builder()
                .name(addProductRequestVo.getName())
                .productStatus(addProductRequestVo.getProductStatus())
                .build();
    }

}
