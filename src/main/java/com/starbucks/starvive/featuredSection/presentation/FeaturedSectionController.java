package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import com.starbucks.starvive.featuredSection.application.FeaturedSectionService;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRegisterRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionCreateRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionUpdateRequestDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<BaseResponseEntity<List<FeaturedSectionResponseDto>>> getSectionList() {
        List<FeaturedSectionResponseDto> result = featuredSectionService.getSectionList();

        return ResponseEntity.ok(new BaseResponseEntity<>(result));
    }
    /**
     * ✅ 2. 추천 섹션 ID 리스트로 섹션별 상품 리스트 조회
     * POST /featured-section/products
     */
    @GetMapping("/featured-section/products")
    public ResponseEntity<BaseResponseEntity<List<FeaturedSectionProductGroupResponseDto>>> getProductsBySection(
            @RequestBody FeaturedSectionProductRequestDto featuredSectionProductRequestDto) {

        List<FeaturedSectionProductGroupResponseDto> result =
                featuredSectionService.getProductsBySections(featuredSectionProductRequestDto.getFeaturedSectionsIds());

        return ResponseEntity.ok(new BaseResponseEntity<>(result));
    }

    @PostMapping("/featured-section")
    public ResponseEntity<BaseResponseEntity<UUID>> createSection(@RequestBody FeaturedSectionCreateRequestDto featuredSectionCreateRequestDto
    ) {
        UUID featuredSectionId = featuredSectionService.createSection(featuredSectionCreateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok(featuredSectionId));
    }

    @PutMapping("/featured-section")
    public ResponseEntity<BaseResponseEntity<Void>> updateSection(
            @PathVariable UUID featuredSectionId,
            @RequestBody FeaturedSectionUpdateRequestDto featuredSectionUpdateRequestDto) {
        featuredSectionService.updateSection(featuredSectionId, featuredSectionUpdateRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    @DeleteMapping("/featured-section")
    public ResponseEntity<BaseResponseEntity<Void>> deleteSection(@PathVariable UUID featuredSectionId) {
        featuredSectionService.deleteSection(featuredSectionId);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }

    @PostMapping("/featured-section/regist-products")
    public ResponseEntity<BaseResponseEntity<Void>> registerProductsToSection(
            @RequestBody FeaturedSectionProductRegisterRequestDto featuredSectionProductRegisterRequestDto
    ) {
        featuredSectionService.registerProductsToSection(featuredSectionProductRegisterRequestDto);
        return ResponseEntity.ok(BaseResponseEntity.ok());
    }
}
