package com.starbucks.starvive.featuredSection.application;


import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductDetailResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeaturedSectionProductService {

    private final FeaturedSectionProductRepository productRepository;

    // 추천 섹션 상품 리스트 조회
    public List<FeaturedSectionProductResponseDto> getProductsBySectionId(UUID featuredSectionId) {
        return productRepository.findByFeaturedSection_FeaturedSectionId(featuredSectionId).stream()
                .map(FeaturedSectionProductResponseDto::from)
                .toList();
    }

    // 추천 섹션 상품 상세 조회
    public FeaturedSectionProductDetailResponseDto getProductDetail(UUID sectionId, UUID productId) {
        FeaturedSectionProduct product = productRepository
                .findByFeaturedSection_FeaturedSectionIdAndFeaturedSectionProductId(sectionId, productId)
                .orElseThrow(() -> new EntityNotFoundException("추천 섹션 상품을 찾을 수 없습니다."));

        return FeaturedSectionProductDetailResponseDto.from(product);
    }

}
