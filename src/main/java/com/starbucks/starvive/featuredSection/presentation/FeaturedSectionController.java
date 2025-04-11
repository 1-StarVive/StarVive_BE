package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.featuredSection.application.FeaturedSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionService featuredSectionService;

//    /**
//     * ✅ 1. 추천 섹션 리스트만 조회
//     * GET /featured-section
//     */
//    @GetMapping("/featured-sections")
//    public ResponseEntity<ErrorResponse<List<FeaturedSectionResponseDto>>> getSectionList() {
//        List<FeaturedSectionResponseDto> result = featuredSectionService.getSectionList();
//
//        return ResponseEntity.ok(new ErrorResponse<>(result));
//    }
//    /**
//     * ✅ 2. 추천 섹션 ID 리스트로 섹션별 상품 리스트 조회
//     * POST /featured-section/products
//     */
//    @GetMapping("/featured-section/products")
//    public ResponseEntity<ErrorResponse<List<FeaturedSectionProductGroupResponseDto>>> getProductsBySection(
//            @RequestBody FeaturedSectionProductRequestDto featuredSectionProductRequestDto) {
//
//        List<FeaturedSectionProductGroupResponseDto> result =
//                featuredSectionService.getProductsBySections(featuredSectionProductRequestDto.getFeaturedSectionsIds());
//
//        return ResponseEntity.ok(new ErrorResponse<>(result));
//    }
//
//    @PostMapping("/featured-section")
//    public ResponseEntity<ErrorResponse<UUID>> createSection(@RequestBody FeaturedSectionCreateRequestDto featuredSectionCreateRequestDto
//    ) {
//        UUID featuredSectionId = featuredSectionService.createSection(featuredSectionCreateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok(featuredSectionId));
//    }
//
//    @PutMapping("/featured-section")
//    public ResponseEntity<ErrorResponse<Void>> updateSection(
//            @PathVariable UUID featuredSectionId,
//            @RequestBody FeaturedSectionUpdateRequestDto featuredSectionUpdateRequestDto) {
//        featuredSectionService.updateSection(featuredSectionId, featuredSectionUpdateRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @DeleteMapping("/featured-section")
//    public ResponseEntity<ErrorResponse<Void>> deleteSection(@PathVariable UUID featuredSectionId) {
//        featuredSectionService.deleteSection(featuredSectionId);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @PostMapping("/featured-section/regist-products")
//    public ResponseEntity<ErrorResponse<Void>> registerProductsToSection(
//            @RequestBody FeaturedSectionProductRegisterRequestDto featuredSectionProductRegisterRequestDto
//    ) {
//        featuredSectionService.registerProductsToSection(featuredSectionProductRegisterRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
}
