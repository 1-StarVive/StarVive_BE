package com.starbucks.starvive.cart.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class CartItemResponseDto {

    private UUID cartId;
    private UUID productId;
    private String productName;
    private int price;
    private int quantity;
}
