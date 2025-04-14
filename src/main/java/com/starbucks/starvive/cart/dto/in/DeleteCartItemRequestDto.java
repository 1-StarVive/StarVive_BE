package com.starbucks.starvive.cart.dto.in;

import com.starbucks.starvive.cart.vo.DeleteCartItemRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteCartItemRequestDto {
    private UUID cartId;

    @Builder
    public DeleteCartItemRequestDto(UUID cartId) {
        this.cartId = cartId;
    }

    public static DeleteCartItemRequestDto from(DeleteCartItemRequestVo deleteCartItemRequestVo) {
        return DeleteCartItemRequestDto.builder()
                .cartId(deleteCartItemRequestVo.getCartId())
                .build();
    }
}