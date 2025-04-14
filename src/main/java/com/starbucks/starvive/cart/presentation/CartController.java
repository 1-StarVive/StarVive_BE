package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCartItemRequestDto;
import com.starbucks.starvive.cart.vo.AddCartItemRequestVo;
import com.starbucks.starvive.cart.vo.CartItemResponseVo;
import com.starbucks.starvive.cart.vo.DeleteCartItemRequestVo;
import com.starbucks.starvive.cart.vo.UpdateCartItemRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 전체 조회", description = "사용자의 장바구니 목록을 조회합니다.", tags = {"cart"})
    @GetMapping("/all")
    public List<CartItemResponseVo> getCartList(@RequestParam("userId") UUID userId) {
        return cartService.getCartList(userId);
    }

    @Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 추가합니다.", tags = {"cart"})
    @PostMapping("/add")
    public void addItem(@RequestBody AddCartItemRequestVo addCartItemRequestVo) {
        cartService.addItem(AddCartItemRequestDto.from(addCartItemRequestVo));
    }

    @Operation(summary = "장바구니 항목 수정", description = "옵션, 수량, 체크 상태를 수정합니다.", tags = {"cart"})
    @PutMapping
    public void updateItem(@RequestBody UpdateCartItemRequestVo updateCartItemRequestVo) {
        cartService.updateItem(UpdateCartItemRequestDto.from(updateCartItemRequestVo));
    }

    @Operation(summary = "장바구니 항목 삭제", description = "장바구니 항목을 삭제합니다.", tags = {"cart"})
    @DeleteMapping
    public void deleteItem(@RequestBody DeleteCartItemRequestVo deleteCartItemRequestVo) {
        cartService.deleteItem(DeleteCartItemRequestDto.from(deleteCartItemRequestVo));
    }

    @Operation(summary = "장바구니 전체 비우기", description = "사용자의 장바구니를 전체 비웁니다.", tags = {"cart"})
    @DeleteMapping("/clear")
    public void clearCart(@RequestParam("userId") UUID userId) {
        cartService.clearCart(userId);
    }
}