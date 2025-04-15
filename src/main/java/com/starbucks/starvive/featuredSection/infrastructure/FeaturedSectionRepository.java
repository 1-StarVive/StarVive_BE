package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeaturedSectionRepository extends JpaRepository<FeaturedSection, UUID > {

    // 단건 조회를 명시적으로 제공
    Optional<FeaturedSection> findByFeaturedSectionId(UUID featuredSectionId);
    // 필요 시 이름으로 중복 체크 등도 가능
    boolean existsByName(String name);

}
