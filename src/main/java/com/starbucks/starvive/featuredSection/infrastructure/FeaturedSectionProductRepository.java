package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeaturedSectionProductRepository extends JpaRepository<FeaturedSectionProduct, UUID> {

    // 추천 섹션 ID 리스트로 해당 상품 목록을 조회
    List<FeaturedSectionProduct> findAllByFeaturedSectionIdIn(List<UUID> featuredSectionIds);

    // 특정 섹션에 속한 모든 상품 조회
    List<FeaturedSectionProduct> findAllByFeaturedSectionId(UUID featuredSectionId);

    // 특정 섹션, 특정 상품 중복 여부 체크
    boolean existsByFeaturedSectionIdAndProductId(UUID featuredSectionId, UUID productId);

    // 삭제 또는 재등록용
    void deleteAllByFeaturedSectionId(UUID featuredSectionId);

}
