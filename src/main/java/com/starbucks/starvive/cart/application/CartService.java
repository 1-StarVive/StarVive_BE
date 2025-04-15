package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCartItemRequestDto;
import com.starbucks.starvive.cart.vo.CartItemResponseVo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface CartService {

    List<CartItemResponseVo> getCartList(UUID userId);
    void addItem(AddCartItemRequestDto addCartItemRequestDto);
    void updateItem(UpdateCartItemRequestDto updateCartItemRequestDto);
    void deleteItem(DeleteCartItemRequestDto deleteCartItemRequestDto);
    void clearCart(UUID userId);

}
