package com.starbucks.starvive.featuredSection.infrastructure;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeaturedSectionRepository extends JpaRepository<FeaturedSection, UUID > {

    @Query("SELECT new com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto(f.featuredSectionId, f.name) " +
            "FROM FeaturedSection f WHERE f.activated = true")
    List<FeaturedSectionResponseDto> findAllActivatedSections();
}
