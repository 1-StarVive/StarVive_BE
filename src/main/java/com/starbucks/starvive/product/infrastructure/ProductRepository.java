package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
        SELECT p.productId AS productId,
               p.name AS name,
               o.baseDiscountRate AS baseDiscountRate,
               o.productOptionId AS productOptionId,
               o.price AS price,
               i.productImageId AS productImageId,
               i.imageThumbUrl AS imageThumbUrl,
               i.imageThumbAlt AS imageThumbAlt
        FROM Product p
        JOIN ProductOption o ON p.productId = o.productId
        JOIN ProductImage i ON p.productId = i.productId
        WHERE i.imageThumbUrl IS NOT NULL
    """)
    List<ProductProjection> findAllWithOptionAndImage();
}
