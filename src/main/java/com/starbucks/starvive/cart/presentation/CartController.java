package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateQuantityRequestDto;
import com.starbucks.starvive.cart.dto.out.*;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<BaseResponseEntity<Void>> addItem(
            @RequestParam UUID userId,
            @RequestBody AddCartItemRequestDto addCartItemRequestDto
    ) {
        cartService.addItem(userId, addCartItemRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    @GetMapping("/cart")
    public ResponseEntity<BaseResponseEntity<List<CartItemResponseDto>>> getList(
            @RequestParam UUID userId
    ) {
        List<CartItemResponseDto> result = cartService.getCartList(userId);
        return ResponseEntity.ok(BaseResponseEntity.ok(result));
    }

    @PatchMapping("/cart/{cartId}")
    public ResponseEntity<BaseResponseEntity<Void>> updateQuantity(
            @PathVariable UUID cartId,
            @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto
    ) {
        cartService.updateQuantity(updateQuantityRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<BaseResponseEntity<Void>> deleteItem(@PathVariable UUID cartId) {
        cartService.deleteItem(cartId);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    @DeleteMapping("/cart/products")
    public ResponseEntity<BaseResponseEntity<Void>> clearCart(
            @RequestBody DeleteCartItemRequestDto deleteCartItemRequestDto
    ) {
        cartService.clearCart(deleteCartItemRequestDto.getUserId());
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }
}
