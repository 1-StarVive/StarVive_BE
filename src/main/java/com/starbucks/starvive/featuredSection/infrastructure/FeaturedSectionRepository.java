package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeaturedSectionRepository extends JpaRepository<FeaturedSection, UUID> {
    List<FeaturedSection> findByFeaturedSectionIdIn(List<UUID> ids);
}
