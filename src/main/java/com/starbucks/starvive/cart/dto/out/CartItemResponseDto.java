package com.starbucks.starvive.cart.dto.out;

import com.starbucks.starvive.cart.vo.CartItemResponseVo;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CartItemResponseDto {

    private UUID userId;
    private UUID cartId;
    private UUID productId;
    private UUID productOptionId;
    private String name;
    private String optionName;
    private String imageThumbUrl;
    private String imageThumbAlt;
    private int price;
    private int baseDiscountRate;
    private int discountedPrice;
    private int quantity;
    private boolean checked;

    @Builder
    public CartItemResponseDto(UUID userId,
                               UUID cartId,
                               UUID productId,
                               UUID productOptionId,
                               String name,
                               String optionName,
                               String imageThumbUrl,
                               String imageThumbAlt,
                               int price,
                               int baseDiscountRate,
                               int discountedPrice,
                               int quantity,
                               boolean checked) {
        this.userId = userId;
        this.cartId = cartId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.name = name;
        this.optionName = optionName;
        this.imageThumbUrl = imageThumbUrl;
        this.imageThumbAlt = imageThumbAlt;
        this.price = price;
        this.baseDiscountRate = baseDiscountRate;
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
        this.checked = checked;
    }

    /**
     * Factory 메서드 – VO 기반 + 할인율 추가 정보로 변환
     */
    public static CartItemResponseDto from(CartItemResponseVo cartItemResponseVo, UUID userId, int baseDiscountRate) {
        int discountedPrice = (baseDiscountRate > 0)
                ? cartItemResponseVo.getPrice() - (cartItemResponseVo.getPrice() * baseDiscountRate / 100)
                : cartItemResponseVo.getPrice();

        return CartItemResponseDto.builder()
                .userId(userId)
                .cartId(cartItemResponseVo.getCartId())
                .productId(cartItemResponseVo.getProductId())
                .productOptionId(cartItemResponseVo.getProductOptionId())
                .name(cartItemResponseVo.getName())
                .imageThumbUrl(cartItemResponseVo.getImageThumbUrl())
                .imageThumbAlt(cartItemResponseVo.getImageThumbAlt())
                .price(cartItemResponseVo.getPrice())
                .baseDiscountRate(baseDiscountRate)
                .discountedPrice(discountedPrice)
                .quantity(cartItemResponseVo.getQuantity())
                .checked(cartItemResponseVo.isChecked())
                .build();
    }
}