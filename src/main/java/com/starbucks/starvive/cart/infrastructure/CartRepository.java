package com.starbucks.starvive.cart.infrastructure;

import com.starbucks.starvive.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    List<Cart> findByUserIdAndDeletedAtIsNull(UUID id);

    Optional<Cart> findByUserIdAndProductOptionIdAndDeletedAtIsNull(UUID id, UUID productOptionId);
}
