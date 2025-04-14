package com.starbucks.starvive.cart.infrastructure;

import com.starbucks.starvive.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    List<Cart> findAllByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);

}
