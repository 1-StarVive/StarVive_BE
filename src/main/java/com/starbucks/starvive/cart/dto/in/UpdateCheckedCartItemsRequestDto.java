package com.starbucks.starvive.cart.dto.in;

import com.starbucks.starvive.cart.vo.UpdateCheckedCartItemsRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateCheckedCartItemsRequestDto {
    private UUID cartId;
    private boolean checked;

    @Builder
    public UpdateCheckedCartItemsRequestDto(UUID cartId, boolean checked) {
        this.cartId = cartId;
        this.checked = checked;
    }

        public static UpdateCheckedCartItemsRequestDto from(UpdateCheckedCartItemsRequestVo updateCheckedCartItemRequestVo) {
        return UpdateCheckedCartItemsRequestDto.builder()
                .cartId(updateCheckedCartItemRequestVo.getCartId())
                .checked(updateCheckedCartItemRequestVo.isChecked())
                .build();
    }
}