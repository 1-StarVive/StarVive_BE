package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.ProductDetailImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface productDetailImageRepository extends JpaRepository<ProductDetailImage, UUID> {

    Optional<ProductDetailImage> findByProductId(UUID productId);
}
