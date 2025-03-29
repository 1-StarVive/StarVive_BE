package com.starbucks.starvive.cart.dto.out;

import java.util.UUID;

public class CartItemResponseDto {

    private UUID cartId;
    private UUID productId;
    private String productName;
    private int price;
    private int quantity;
}
