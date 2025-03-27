package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.featuredSection.application.FeaturedSectionProductService;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/featuredSections")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionProductService sectionProductService;

    // 추천 섹션 상품 리스트
    @GetMapping("/{sectionId}/products")
    public ResponseEntity<List<FeaturedSectionProductResponseDto>> getProductsInSection(
            @PathVariable UUID sectionId
    ) {
        return ResponseEntity.ok(sectionProductService.getProductsBySectionId(sectionId));
    }

    // 추천 섹션 상품 상세 조회
    @GetMapping("/{sectionId}/products/{productId}")
    public ResponseEntity<FeaturedSectionProductDetailResponseDto> getProductDetail(
            @PathVariable UUID sectionId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(sectionProductService.getProductDetail(sectionId, productId));
    }
}
