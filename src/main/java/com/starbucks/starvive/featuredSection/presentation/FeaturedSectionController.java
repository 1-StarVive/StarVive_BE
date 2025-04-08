package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.featuredSection.application.FeaturedSectionService;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRequestDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionService featuredSectionService;

    /**
     * ✅ 1. 추천 섹션 리스트만 조회
     * GET /featured-section
     */
    @GetMapping("/featured-sections")
    public ResponseEntity<List<FeaturedSectionResponseDto>> getSectionList() {
        return ResponseEntity.ok(featuredSectionService.getSectionList());
    }

    /**
     * ✅ 2. 추천 섹션 ID 리스트로 섹션별 상품 리스트 조회
     * POST /featured-section/products
     */
    @GetMapping("/featured-section/products")
    public ResponseEntity<List<FeaturedSectionProductGroupResponseDto>> getProductsBySection(
            @RequestBody FeaturedSectionProductRequestDto featuredSectionProductRequestDto) {
        return ResponseEntity.ok(featuredSectionService.getProductsBySections(featuredSectionProductRequestDto.getFeaturedSectionsIds()));
    }

    //@PostMapping("/featured-section")


    //@PutMapping("/featured-section")


    //@DeleteMapping("/featured-section")


   // @PostMapping("/featured-section/regist-products")



}
