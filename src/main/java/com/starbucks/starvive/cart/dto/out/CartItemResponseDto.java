package com.starbucks.starvive.cart.dto.out;

import com.starbucks.starvive.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartItemResponseDto {

    private UUID userId;
    private UUID cartId;
    private UUID productId;
    private String productName;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private int price;
    private int quantity;

}