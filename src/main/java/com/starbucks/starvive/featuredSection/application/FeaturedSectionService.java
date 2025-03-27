package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductListBySectionResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeaturedSectionService {

    private final FeaturedSectionRepository sectionRepository;

    public List<FeaturedSectionResponseDto> getAllSections() {
        return sectionRepository.findAll().stream()
                .map(FeaturedSectionResponseDto::from)
                .toList();
    }

    public List<FeaturedSectionProductListBySectionResponseDto> getProductsBySectionIds(List<UUID> sectionIds) {
        return sectionRepository.findByFeaturedSectionIdIn(sectionIds).stream()
                .map(section -> FeaturedSectionProductListBySectionResponseDto.builder()
                        .featuredSectionsId(section.getFeaturedSectionId())
                        .products(section.getProducts().stream()
                                .map(FeaturedSectionProductResponseDto::from)
                                .toList())
                        .build())
                .toList();
    }
}
