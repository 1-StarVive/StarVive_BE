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
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping
    public ResponseEntity<AddCartItemResponseDto> add(
            @RequestParam UUID userId,
            @RequestBody AddCartItemRequestDto dto) {
        return ResponseEntity.ok(cartService.addItem(userId, dto));
    }
    
    // 장바구니 목록 조회
    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCart(@RequestParam UUID userId) {
        return ResponseEntity.ok(cartService.getCartList(userId));
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/{cartId}")
    public ResponseEntity<UpdateQuantityResponseDto> updateQuantity(
            @PathVariable UUID cartId,
            @RequestBody UpdateQuantityRequestDto dto) {
        return ResponseEntity.ok(cartService.updateQuantity(cartId, dto.getQuantity()));
    }

    // 장바구니 개별 상품 삭제
    @DeleteMapping
    public ResponseEntity<DeleteCartItemResponseDto> delete(@RequestBody DeleteCartItemRequestDto dto) {
        return ResponseEntity.ok(cartService.deleteItem(dto.getCartId()));
    }

    // 장바구니 전체 비우기
    @DeleteMapping("/items")
    public ResponseEntity<ClearCartResponseDto> clearCart(@RequestParam UUID userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}
