package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.DeleteWishRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteWishRequestDto {
    private UUID userId;
    private UUID productId;

    @Builder
    public DeleteWishRequestDto(UUID userId, UUID productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public static DeleteWishRequestDto from(DeleteWishRequestVo deleteWishRequestVo, UUID userId) {
        return DeleteWishRequestDto.builder()
                .userId(userId)
                .productId(deleteWishRequestVo.getProductId())
                .build();
    }
}
