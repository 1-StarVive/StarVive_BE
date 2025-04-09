package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.projection.ProductDetailProjection;
import com.starbucks.starvive.product.projection.ProductProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
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

    @Query("""
        SELECT
            p.productId AS productId,
            p.name AS name,
            o.price AS price,
            i.imageThumbUrl AS imageThumbUrl,
            i.imageThumbAlt AS imageThumbAlt,
            d.productDetailContent AS productDetailContent
        FROM Product p
        JOIN ProductOption o ON p.productId = o.productId
        JOIN ProductImage i ON p.productId = i.productId
        JOIN ProductDetailImage d ON p.productId = d.productId
        WHERE p.productId = :productId
    """)
    Optional<ProductDetailProjection> findProductDetailByProductId(@Param("productId") UUID productId);

}
