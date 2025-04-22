package com.starbucks.starvive.cart.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdateCheckedCartItemsRequestVo {

    private UUID cartId;
    private boolean checked;

}