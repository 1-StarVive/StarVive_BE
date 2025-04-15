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
    private UUID productOptionId;

    @Builder
    public AddWishRequestDto(UUID userId, UUID productId, UUID productOptionId) {
        this.userId = userId;
        this.productId = productId;
        this.productOptionId = productOptionId;
    }

    public static AddWishRequestDto from(AddWishRequestVo addWishRequestVo) {
        return AddWishRequestDto.builder()
                .userId(addWishRequestVo.getUserId())
                .productId(addWishRequestVo.getProductId())
                .productOptionId(addWishRequestVo.getProductOptionId())
                .build();
    }
}
