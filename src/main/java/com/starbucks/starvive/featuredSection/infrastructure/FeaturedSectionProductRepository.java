package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.Projection.FeaturedSectionProductProjection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeaturedSectionProductRepository extends JpaRepository<FeaturedSectionProduct, UUID> {

    @Query("""
    SELECT f.featuredSectionId AS featuredSectionId,
           p.productId AS productId,
           i.imageThumbUrl AS imageThumbUrl,
           i.imageThumbAlt AS imageThumbAlt,
           p.name AS name,
           o.price AS price,
           o.baseDiscountRate AS discountRate
    FROM FeaturedSectionProduct f
    JOIN Product p ON f.productId = p.productId
    JOIN ProductImage i ON p.productId = i.productId
    JOIN ProductOption o ON p.productId = o.productId
    WHERE f.featuredSectionId IN :sectionIds AND i.imageThumbUrl = 'Y'
""")
    List<FeaturedSectionProductProjection> findProductsBySectionIds(@Param("sectionIds") List<UUID> sectionIds);
}
