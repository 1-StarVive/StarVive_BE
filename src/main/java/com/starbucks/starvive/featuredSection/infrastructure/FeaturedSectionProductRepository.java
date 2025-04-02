package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeaturedSectionProductRepository extends JpaRepository<FeaturedSectionProduct, UUID> {

    List<FeaturedSectionProduct> findByFeaturedSectionId(UUID featuredSectionId);
}
