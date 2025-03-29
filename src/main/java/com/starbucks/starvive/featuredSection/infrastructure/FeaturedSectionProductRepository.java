package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FeaturedSectionProductRepository extends JpaRepository<FeaturedSectionProduct, UUID> {
    List<FeaturedSectionProduct> findByFeaturedSection(FeaturedSection featuredSection);
}
