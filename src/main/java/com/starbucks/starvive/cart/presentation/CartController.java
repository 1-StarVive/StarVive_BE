package com.starbucks.starvive.cart.presentation;

import com.starbucks.starvive.cart.application.CartService;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteSelectedCartItemsRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCartItemRequestDto;
import com.starbucks.starvive.cart.vo.AddCartItemRequestVo;
import com.starbucks.starvive.cart.vo.CartItemResponseVo;
import com.starbucks.starvive.cart.vo.DeleteSelectedCartItemsRequestVo;
import com.starbucks.starvive.cart.vo.UpdateCartItemRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.starbucks.starvive.user.domain.User;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private UUID getUserId(UserDetails userDetails) {
        User user = (User) userDetails;
        return user.getUserId();
    }

    @Operation(summary = "장바구니 전체 조회", description = "사용자의 장바구니 목록을 조회합니다.", tags = {"cart"})
    @GetMapping("/all")
    public List<CartItemResponseVo> getCartList(@AuthenticationPrincipal UserDetails userDetails) {

        return cartService.getCartList(getUserId(userDetails));
    }

    @Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 추가합니다.", tags = {"cart"})
    @PostMapping("/add")
    public void addItem(@RequestBody AddCartItemRequestVo addCartItemRequestVo, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.addItem(AddCartItemRequestDto.from(addCartItemRequestVo, getUserId(userDetails)), getUserId(userDetails));
    }

    @Operation(summary = "장바구니 항목 수정", description = "옵션, 수량, 체크 상태를 수정합니다.", tags = {"cart"})
    @PutMapping("/items")
    public void updateItem(@RequestBody UpdateCartItemRequestVo updateCartItemRequestVo, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.updateItem(UpdateCartItemRequestDto.from(updateCartItemRequestVo), getUserId(userDetails));
    }

    @Operation(summary = "장바구니 선택 항목 삭제", description = "장바구니에서 여러 개의 항목을 선택하여 삭제합니다.", tags = {"cart"})
    @DeleteMapping("/items")
    public void deleteSelectedItems(@RequestBody DeleteSelectedCartItemsRequestVo deleteSelectedCartItemsRequestVo, @AuthenticationPrincipal UserDetails userDetails) {
        cartService.deleteSelectedItems(DeleteSelectedCartItemsRequestDto.from(deleteSelectedCartItemsRequestVo), getUserId(userDetails));
    }
}