package com.starbucks.starvive.cart.application;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.dto.in.AddCartItemRequestDto;
import com.starbucks.starvive.cart.dto.in.DeleteSelectedCartItemsRequestDto;
import com.starbucks.starvive.cart.dto.in.UpdateCartItemRequestDto;
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
        List<Cart> carts = cartRepository.findAllByUserId(userId);

        return carts.stream().map(cart -> {
            Product product = productRepository.findById(cart.getProductId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));

            ProductOption option = productOptionRepository.findById(cart.getProductOptionId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));

            ProductImage image = productImageRepository.findFirstByProductId(cart.getProductId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

            return CartItemResponseVo.from(cart, product, option, image);
        }).collect(Collectors.toList());
    }

    @Override
    public void addItem(AddCartItemRequestDto addCartItemRequestDto) {
        Cart cart = Cart.builder()
                .userId(addCartItemRequestDto.getUserId())
                .productId(addCartItemRequestDto.getProductId())
                .productOptionId(addCartItemRequestDto.getProductOptionId())
                .quantity(addCartItemRequestDto.getQuantity())
                .checked(addCartItemRequestDto.isChecked())
                .build();
        cartRepository.save(cart);
    }

    @Override
    public void updateItem(UpdateCartItemRequestDto updateCartItemRequestDto) {
        Cart cart = cartRepository.findById(updateCartItemRequestDto.getCartId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CART));
        cart.update(updateCartItemRequestDto.getProductOptionId(), updateCartItemRequestDto.getQuantity(), updateCartItemRequestDto.isChecked());
    }

    @Override
    public void deleteSelectedItems(DeleteSelectedCartItemsRequestDto deleteSelectedCartItemsRequestDto) {
        List<UUID> cartItemIds = deleteSelectedCartItemsRequestDto.getCartItemIds();

        // 장바구니 항목이 존재하는지 검증 (선택 사항)
        cartItemIds.forEach(cartId -> {
            if (!cartRepository.existsById(cartId)) {
                throw new BaseException(NO_EXIST_CART);
            }
        });

        cartRepository.deleteAllByIdInBatch(cartItemIds);
    }
}
