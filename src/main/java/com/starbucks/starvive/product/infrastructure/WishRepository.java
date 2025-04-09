package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Wish;
import com.starbucks.starvive.product.projection.WishProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishRepository extends JpaRepository<Wish, UUID> {

    Optional<Wish> findByUserIdAndProductId(UUID userId, UUID productId);

    @Query("SELECT w.wishId AS wishId, " +
            "p.productId AS productId, " +
            "p.name AS name, " +
            "o.price AS price, " +
            "i.imageThumbUrl AS imageThumbUrl, " +
            "i.imageThumbAlt AS imageThumbAlt, " +
            "o.baseDiscountRate AS discountRate " +
            "FROM Wish w " +
            "JOIN ProductOption o ON w.productOptionId = o.productOptionId " +
            "JOIN Product p ON o.productId = p.productId " +
            "JOIN ProductImage i ON p.productId = i.productId " +
            "WHERE w.userId = :userId AND i.imageThumbUrl = 'Y'")
    List<WishProductProjection> findByUserId(UUID userId);
}
