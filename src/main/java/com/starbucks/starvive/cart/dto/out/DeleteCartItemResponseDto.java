package com.starbucks.starvive.cart.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class DeleteCartItemResponseDto {

    private String message;
    private UUID cartId;
}
