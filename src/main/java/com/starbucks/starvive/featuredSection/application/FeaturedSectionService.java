package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionCreateRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRegisterRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionUpdateRequestDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import java.util.List;
import java.util.UUID;


public interface FeaturedSectionService {

    List<FeaturedSectionResponseDto> getSectionList();
    List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds);

    UUID createSection(FeaturedSectionCreateRequestDto featuredSectionCreateRequestDto);
    void updateSection(UUID featuredSectionId, FeaturedSectionUpdateRequestDto featuredSectionUpdateRequestDto);
    void deleteSection(UUID featuredSectionId);
    void registerProductsToSection(FeaturedSectionProductRegisterRequestDto featuredSectionProductRegisterRequestDto);


}