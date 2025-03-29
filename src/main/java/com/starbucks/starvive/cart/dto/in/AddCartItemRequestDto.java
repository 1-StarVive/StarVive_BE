package com.starbucks.starvive.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequestDto {

    private UUID productId;
    private int quantity;
}
