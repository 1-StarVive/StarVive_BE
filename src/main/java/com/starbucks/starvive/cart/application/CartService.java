package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateQuantityRequestDto;
import com.starbucks.starvive.cart.dto.out.CartItemResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface CartService {

    void addItem(UUID userId, AddCartItemRequestDto addCartItemRequestDto);
    List<CartItemResponseDto> getCartList(UUID userId);
    void updateQuantity(UpdateQuantityRequestDto updateQuantityRequestDto);
    void deleteItem(UUID cartId);
    void clearCart(UUID userId);

}
