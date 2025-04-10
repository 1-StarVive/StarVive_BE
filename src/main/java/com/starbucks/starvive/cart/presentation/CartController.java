package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateQuantityRequestDto;
import com.starbucks.starvive.cart.dto.out.*;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public BaseResponseEntity<Void> addItem(
            @RequestParam UUID userId,
            @RequestBody AddCartItemRequestDto addCartItemRequestDto
    ) {
        cartService.addItem(userId, addCartItemRequestDto);
        return BaseResponseEntity.ok();
    }

    @GetMapping("/cart")
    public BaseResponseEntity<List<CartItemResponseDto>> getList(
            @RequestParam UUID userId
    ) {
        List<CartItemResponseDto> result = cartService.getCartList(userId);
        return BaseResponseEntity.ok(result);
    }

    @PatchMapping("/cart/{cartId}")
    public BaseResponseEntity<Void> updateQuantity(
            @PathVariable UUID cartId,
            @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto
    ) {
        cartService.updateQuantity(updateQuantityRequestDto);
        return BaseResponseEntity.ok();
    }


    @DeleteMapping("/cart/{cartId}")
    public BaseResponseEntity<Void> deleteItem(@PathVariable UUID cartId) {
        cartService.deleteItem(cartId);
        return BaseResponseEntity.ok();
    }

    @DeleteMapping("/cart/products")
    public BaseResponseEntity<Void> clearCart(
            @RequestBody DeleteCartItemRequestDto deleteCartItemRequestDto
    ) {
        cartService.clearCart(deleteCartItemRequestDto.getUserId());
        return BaseResponseEntity.ok();
    }
}
