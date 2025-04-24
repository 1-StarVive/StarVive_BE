package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteSelectedCartItemsRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCheckedCartItemsRequestDto;
import com.starbucks.starvive.cart.infrastructure.CartRepository;
import com.starbucks.starvive.cart.vo.CartItemResponseVo;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CartItemResponseVo> getCartList(UUID userId) {
        return cartRepository.findAllByUserId(userId).stream()
                .map(cart -> {
                    Product product = productRepository.findById(cart.getProductId())
                            .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));

                    ProductOption option = productOptionRepository.findById(cart.getProductOptionId())
                            .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));

                    ProductImage image = productImageRepository.findFirstByProductId(cart.getProductId())
                            .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

                    return CartItemResponseVo.from(cart, product, option, image);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addItem(AddCartItemRequestDto addCartItemRequestDto, UUID userId) {
        UUID productOptionId = addCartItemRequestDto.getProductOptionId();

        List<Cart> existingCarts = cartRepository.findAllByUserIdAndProductOptionId(userId, productOptionId);

        if (!existingCarts.isEmpty()) {
            Cart mainCart = existingCarts.get(0);
            mainCart.increaseQuantity(addCartItemRequestDto.getQuantity());

            if (existingCarts.size() > 1) {
                List<Cart> redundant = existingCarts.subList(1, existingCarts.size());
                cartRepository.deleteAll(redundant);
            }
        } else {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .productId(addCartItemRequestDto.getProductId())
                    .productOptionId(productOptionId)
                    .quantity(addCartItemRequestDto.getQuantity())
                    .checked(addCartItemRequestDto.isChecked())
                    .build();
            cartRepository.save(newCart);
        }
    }

    @Transactional
    @Override
    public void updateItem(UpdateCartItemRequestDto updateCartItemRequestDto, UUID userId) {
        Cart cart = cartRepository.findById(updateCartItemRequestDto.getCartId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CART));

        if (!cart.getUserId().equals(userId)) {
            throw new BaseException(NO_PERMISSION_CART);
        }

        cart.update(updateCartItemRequestDto.getProductOptionId(), updateCartItemRequestDto.getQuantity(), updateCartItemRequestDto.isChecked());
    }

    @Transactional
    @Override
    public void updateCheckedItems(List<UpdateCheckedCartItemsRequestDto> dtoList, UUID userId) {
        for (UpdateCheckedCartItemsRequestDto dto : dtoList) {
            Cart cart = cartRepository.findByCartIdAndUserId(dto.getCartId(), userId)
                    .orElseThrow(() -> new BaseException(NO_EXIST_CART));

            cart.updateChecked(dto.isChecked());
        }
    }

    @Override
    public void deleteSelectedItems(DeleteSelectedCartItemsRequestDto deleteSelectedCartItemsRequestDto, UUID userId) {
        List<UUID> cartItemIds = deleteSelectedCartItemsRequestDto.getCartItemIds();

        List<Cart> carts = cartRepository.findAllById(cartItemIds);

        boolean hasInvalidCart = carts.stream()
                .anyMatch(cart -> !cart.getUserId().equals(userId));

        if (hasInvalidCart) {
            throw new BaseException(NO_PERMISSION_CART);
        }

        cartRepository.deleteAllInBatch(carts);
    }
}