package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeaturedSectionRepository extends JpaRepository<FeaturedSection, UUID > {
    
    // 활성화된 추천 섹션만 조회
    List<FeaturedSection> findByActivatedTrue();
}
