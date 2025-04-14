package com.starbucks.starvive.cart.dto.in;

import com.starbucks.starvive.cart.vo.UpdateCartItemRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateCartItemRequestDto {
    private UUID cartId;
    private UUID productOptionId;
    private int quantity;
    private boolean checked;

    @Builder
    public UpdateCartItemRequestDto(UUID cartId, UUID productOptionId, int quantity, boolean checked) {
        this.cartId = cartId;
        this.productOptionId = productOptionId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public static UpdateCartItemRequestDto from(UpdateCartItemRequestVo updateCartItemRequestVo) {
        return UpdateCartItemRequestDto.builder()
                .cartId(updateCartItemRequestVo.getCartId())
                .productOptionId(updateCartItemRequestVo.getProductOptionId())
                .quantity(updateCartItemRequestVo.getQuantity())
                .checked(updateCartItemRequestVo.isChecked())
                .build();
    }
}