package com.starbucks.starvive.cart.dto.in;

import com.starbucks.starvive.cart.vo.AddCartItemRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddCartItemRequestDto {
    private UUID userId;
    private UUID productId;
    private UUID productOptionId;
    private int quantity;
    private boolean checked;

    @Builder
    public AddCartItemRequestDto(UUID userId, UUID productId, UUID productOptionId, int quantity, boolean checked) {
        this.userId = userId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public static AddCartItemRequestDto from(AddCartItemRequestVo addCartItemRequestVo, UUID userId) {
        return AddCartItemRequestDto.builder()
                .userId(userId)
                .productId(addCartItemRequestVo.getProductId())
                .productOptionId(addCartItemRequestVo.getProductOptionId())
                .quantity(addCartItemRequestVo.getQuantity())
                .checked(addCartItemRequestVo.isChecked())
                .build();
    }
}