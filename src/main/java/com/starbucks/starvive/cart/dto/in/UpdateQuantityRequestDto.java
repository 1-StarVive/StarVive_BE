package com.starbucks.starvive.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateQuantityRequestDto {

    private UUID cartId;
    private int quantity;

}
