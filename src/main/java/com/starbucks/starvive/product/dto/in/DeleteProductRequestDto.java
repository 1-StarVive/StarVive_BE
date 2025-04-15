package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.DeleteProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteProductRequestDto {
    private UUID productId;

    @Builder
    public DeleteProductRequestDto(UUID productId) {
        this.productId = productId;
    }

    public static DeleteProductRequestDto from(DeleteProductRequestVo vo) {
        return DeleteProductRequestDto.builder()
                .productId(vo.getProductId())
                .build();
    }
}