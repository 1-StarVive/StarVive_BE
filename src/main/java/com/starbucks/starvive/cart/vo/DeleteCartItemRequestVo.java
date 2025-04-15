package com.starbucks.starvive.cart.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteCartItemRequestVo {

    private UUID cartId;

}
