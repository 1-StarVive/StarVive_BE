package com.starbucks.starvive.cart.infrastructure;

import com.starbucks.starvive.cart.domain.Cart;
import com.starbucks.starvive.cart.projection.CartItemListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {


    List<Cart> findByUserId(UUID userId);

    Optional<Cart> findByUserIdAndProductOptionId(UUID userId, UUID productOptionId);

    void deleteAllByUserId(UUID userId);

    @Query("SELECT c.cartId AS cartId, " +
            "p.productId AS productId, " +
            "p.name AS productName, " +
            "i.imageThumbUrl AS imageThumbUrl, " +
            "i.imageThumbAlt AS imageThumbAlt, " +
            "o.price AS price, " +
            "c.quantity AS quantity " +
            "FROM Cart c " +
            "JOIN ProductOption o ON c.productOptionId = o.productOptionId " +
            "JOIN Product p ON o.productId = p.productId " +
            "JOIN ProductImage i ON p.productId = i.productId " +
            "WHERE c.userId = :userId " +
            "AND i.imageThumbUrl = 'Y'")  // 썸네일만 조회
    List<CartItemListProjection> findCartItemListByUserId(UUID userId);


}
