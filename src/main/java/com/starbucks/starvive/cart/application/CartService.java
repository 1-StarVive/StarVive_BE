package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.out.*;
import com.starbucks.starvive.cart.infrastructure.CartRepository;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

// 작성자 : 김보미

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;

    public AddCartItemResponseDto addItem(UUID userId, AddCartItemRequestDto dto) {
        // 상품 추가
        Cart cart = cartRepository.findByUserIdAndProductOptionIdAndDeletedAtIsNull(userId, dto.getProductId())
                .orElse(Cart.builder()
                        .userId(userId)
                        .productOptionId(dto.getProductId())
                        .quantity(0)
                        .date(new Date())
                        .checked(true)
                        .build());

        cart.updateQuantity(cart.getQuantity() + dto.getQuantity());
        cartRepository.save(cart);

        return new AddCartItemResponseDto("상품이 장바구니에 추가되었습니다.", cart.getQuantity());
    }


    public List<CartItemResponseDto> getCartList(UUID userId) {
        return cartRepository.findByUserIdAndDeletedAtIsNull(userId).stream()
                .map(c -> {
                    ProductOption option = productOptionRepository.findById(c.getProductOptionId())
                            .orElseThrow(() -> new NoSuchElementException("상품 옵션을 찾을 수 없습니다."));
                    return new CartItemResponseDto(
                            c.getCartId(),
                            c.getProductOptionId(),
                            option.getProduct().getName(),
                            option.getPrice(),
                            c.getQuantity());
                }).toList();
    }

    public UpdateQuantityResponseDto updateQuantity(UUID cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.updateQuantity(quantity);
        return new UpdateQuantityResponseDto("상품 수량이 변경되었습니다.", cartId, cart.getQuantity());
    }

    public DeleteCartItemResponseDto deleteItem(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.softDelete();
        return new DeleteCartItemResponseDto("상품이 장바구니에 삭제되었습니다.", cartId);
    }

    public ClearCartResponseDto clearCart(UUID userId) {
        cartRepository.findByUserIdAndDeletedAtIsNull(userId).forEach(Cart::softDelete);
        return new ClearCartResponseDto("장바구니가 비워졌습니다.");
    }


}
