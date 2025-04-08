package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import com.starbucks.starvive.featuredSection.vo.FeaturedSectionProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeaturedSectionServiceImpl implements FeaturedSectionService {

    private final FeaturedSectionRepository sectionRepository;
    private final FeaturedSectionProductRepository productRepository;


    @Override
    public List<FeaturedSectionResponseDto> getSectionList() {
        return sectionRepository.findAllActivatedSections();
    }

    @Override
    public List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds) {
        List<FeaturedSectionProductVo> products = productRepository.findProductsBySectionIds(sectionIds).stream()
                .map(FeaturedSectionProductVo::new)
                .toList();

        Map<UUID, List<FeaturedSectionProductResponseDto>> productMap = products.stream()
                .collect(Collectors.groupingBy(
                        FeaturedSectionProductVo::getFeaturedSectionId,
                        Collectors.mapping(FeaturedSectionProductVo::toItemDto, Collectors.toList())
                ));

        return sectionIds.stream()
                .map(id -> new FeaturedSectionProductGroupResponseDto(id, productMap.getOrDefault(id, List.of())))
                .toList();
    }

}
