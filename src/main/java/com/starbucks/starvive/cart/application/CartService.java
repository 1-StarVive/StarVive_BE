package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.out.*;
import com.starbucks.starvive.cart.infrastructure.CartRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
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
    private final ProductRepository productRepository;

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

                    // 👉 여기 추가: 연관관계 없이 productId로 직접 조회
                    UUID productId = option.getProductId();  // ProductOption 엔티티에 이 필드가 있어야 합니다
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));

                    return new CartItemResponseDto(
                            c.getCartId(),
                            c.getProductOptionId(),
                            product.getName(),
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
