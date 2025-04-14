package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishRepository extends JpaRepository<Wish, UUID> {

    Optional<Wish> findByUserIdAndProductOptionId(UUID userId, UUID productOptionId);
    List<Wish> findAllByUserId(UUID userId);

}
