package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.AddWishRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddWishRequestDto {

    private UUID userId;
    private UUID productId;

    @Builder
    public AddWishRequestDto(UUID userId, UUID productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public static AddWishRequestDto from(AddWishRequestVo addWishRequestVo, UUID userId) {
        return AddWishRequestDto.builder()
                .userId(userId)
                .productId(addWishRequestVo.getProductId())
                .build();
    }
}
