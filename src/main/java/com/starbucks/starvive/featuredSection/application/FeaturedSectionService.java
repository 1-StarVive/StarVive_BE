package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import java.util.List;
import java.util.UUID;


public interface FeaturedSectionService {

    List<FeaturedSectionResponseDto> getSectionList();
    List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds);

//    UUID createSection(FeaturedSectionRequestDto request);
//    void updateSection(UUID id, FeaturedSectionRequestDto request);
//    void deleteSection(UUID id);
//
//    UUID registerProductToSection(FeaturedSectionProductRegisterRequestDto request);


}