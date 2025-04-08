package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateQuantityRequestDto;
import com.starbucks.starvive.cart.dto.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

// 작성자 : 김보미

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestBody UUID userId, AddCartItemRequestDto addCartItemRequestDto) {
        cartService.addItem(userId, addCartItemRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getList(@RequestParam UUID userId) {
        return ResponseEntity.ok(cartService.getCartList(userId));
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> updateQuantity(@RequestBody UpdateQuantityRequestDto updateQuantityRequestDto) {
        cartService.updateQuantity(updateQuantityRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID cartId) {
        cartService.deleteItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> clearCart(@RequestBody DeleteCartItemRequestDto deleteCartItemRequestDto) {
        cartService.clearCart(deleteCartItemRequestDto.getUserId());
        return ResponseEntity.noContent().build();
    }
}
