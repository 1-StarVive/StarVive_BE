package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

//    @PostMapping("/cart")
//    public ResponseEntity<ErrorResponse<Void>> addItem(
//            @RequestParam UUID userId,
//            @RequestBody AddCartItemRequestDto addCartItemRequestDto
//    ) {
//        cartService.addItem(userId, addCartItemRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @GetMapping("/cart")
//    public ResponseEntity<ErrorResponse<List<CartItemResponseDto>>> getList(
//            @RequestParam UUID userId
//    ) {
//        List<CartItemResponseDto> result = cartService.getCartList(userId);
//        return ResponseEntity.ok(ErrorResponse.ok(result));
//    }
//
//    @PatchMapping("/cart/{cartId}")
//    public ResponseEntity<ErrorResponse<Void>> updateQuantity(
//            @PathVariable UUID cartId,
//            @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto
//    ) {
//        cartService.updateQuantity(updateQuantityRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @DeleteMapping("/cart/{cartId}")
//    public ResponseEntity<ErrorResponse<Void>> deleteItem(@PathVariable UUID cartId) {
//        cartService.deleteItem(cartId);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @DeleteMapping("/cart/products")
//    public ResponseEntity<ErrorResponse<Void>> clearCart(
//            @RequestBody DeleteCartItemRequestDto deleteCartItemRequestDto
//    ) {
//        cartService.clearCart(deleteCartItemRequestDto.getUserId());
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
}
