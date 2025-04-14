package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.dto.in.*;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import java.util.List;
import java.util.UUID;

public interface FeaturedSectionService {

    List<FeaturedSection> getSectionList();
    FeaturedSection getSection(UUID featuredSectionId);
    void createSection(AddFeaturedSectionRequestDto addFeaturedSectionRequestDto);
    void updateSection(UpdateFeaturedSectionRequestDto updateFeaturedSectionRequestDto);
    void deleteSection(DeleteFeaturedSectionRequestDto deleteFeaturedSectionRequestDto);
    List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> featuredSectionIds);
    void registerSingleProduct(AddFeaturedSectionSingleProductRequestDto addFeaturedSectionSingleProductRequestDto);
    void registerProducts(AddFeaturedSectionProductRequestDto addFeaturedSectionProductRequestDto);

}