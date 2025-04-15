package com.starbucks.starvive.cart.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateCartItemRequestVo {

    private UUID cartId;
    private UUID productOptionId;
    private int quantity;
    private boolean checked;

}
