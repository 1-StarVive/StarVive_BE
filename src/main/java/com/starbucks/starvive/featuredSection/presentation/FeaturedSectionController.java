package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.featuredSection.application.FeaturedSectionService;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRequestDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/featured-Sections")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionService sectionService;

    // 추천 섹션 리스트 (이름과 ID만 반환)
     @GetMapping
     public ResponseEntity<List<FeaturedSectionResponseDto>> getSections() {
         return ResponseEntity.ok(sectionService.getOnlySections());
     }

     //  특정 섹션 ID 배열로 섹션 + 상품 목록 조회
     @PostMapping("/products")
     public ResponseEntity<List<FeaturedSectionProductResponseDto>> getSectionProducts(
             @RequestBody FeaturedSectionProductRequestDto request
     ) {
         return ResponseEntity.ok(
                 sectionService.getSectionsByIds(request.getFeaturedSectionsIds())
         );
     }
}
