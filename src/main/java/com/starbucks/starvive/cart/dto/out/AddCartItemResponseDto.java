package com.starbucks.starvive.cart.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AddCartItemResponseDto {
    private String message;
    private int quantity;
}
