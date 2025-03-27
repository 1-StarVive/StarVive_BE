package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeaturedSectionProductRepository extends JpaRepository<FeaturedSectionProduct, UUID> {

    // 섹션별 상품 리스트
    List<FeaturedSectionProduct> findByFeaturedSection_FeaturedSectionId(UUID featuredSectionId);;

    // 섹션 상품 상세
    Optional<FeaturedSectionProduct> findByFeaturedSection_FeaturedSectionIdAndFeaturedSectionProductId(UUID sectionId, UUID productId);
}
