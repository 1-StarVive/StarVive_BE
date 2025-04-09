package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateQuantityRequestDto;
import com.starbucks.starvive.cart.dto.out.CartItemResponseDto;
import com.starbucks.starvive.cart.infrastructure.CartRepository;
import com.starbucks.starvive.cart.vo.CartItemVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void addItem(UUID userId, AddCartItemRequestDto addCartItemRequestDto) {
        Cart cart = cartRepository.findByUserIdAndProductOptionId(userId, addCartItemRequestDto.getProductOptionId())
                .orElse(new Cart(userId, addCartItemRequestDto.getProductOptionId(), 0));
        cart.increaseQuantity(addCartItemRequestDto.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemResponseDto> getCartList(UUID userId) {
        return cartRepository.findCartItemListByUserId(userId).stream()
                .map(CartItemVo::new)
                .map(CartItemVo::toResponseDto)
                .toList();
    }

    @Override
    public void updateQuantity(UpdateQuantityRequestDto updateQuantityRequestDto) {
        cartRepository.findById(updateQuantityRequestDto.getCartId()).ifPresent(c -> c.updateQuantity(updateQuantityRequestDto.getQuantity()));
    }

    @Override
    public void  deleteItem(UUID cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void clearCart(UUID userId) {
        cartRepository.deleteAll(cartRepository.findByUserId(userId));
    }
}
