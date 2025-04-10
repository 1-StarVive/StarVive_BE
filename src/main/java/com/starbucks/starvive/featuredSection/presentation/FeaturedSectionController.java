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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionService featuredSectionService;

    /**
     *  1. 추천 섹션 리스트 조회
     * GET /api/featured-sections
     */
    @GetMapping("/featured-sections")
    public BaseResponseEntity<List<FeaturedSectionResponseDto>> getSectionList() {
        List<FeaturedSectionResponseDto> result = featuredSectionService.getSectionList();
        return BaseResponseEntity.ok(result);
    }

    /**
     *  2. 추천 섹션 ID 리스트로 섹션별 상품 리스트 조회
     * GET /api/featured-section/products
     */
    @GetMapping("/featured-section/products")
    public BaseResponseEntity<List<FeaturedSectionProductGroupResponseDto>> getProductsBySection(
            @RequestBody FeaturedSectionProductRequestDto featuredSectionProductRequestDto) {

        List<FeaturedSectionProductGroupResponseDto> result =
                featuredSectionService.getProductsBySections(featuredSectionProductRequestDto.getFeaturedSectionsIds());

        return BaseResponseEntity.ok(result);
    }

    /**
     *  3. 추천 섹션 등록
     * POST /api/featured-section
     */
    @PostMapping("/featured-section")
    public BaseResponseEntity<UUID> createSection(@RequestBody FeaturedSectionCreateRequestDto featuredSectionCreateRequestDto) {
        UUID sectionId = featuredSectionService.createSection(featuredSectionCreateRequestDto);
        return BaseResponseEntity.ok(sectionId);
    }

    /**
     *  4. 추천 섹션 수정
     * PUT /api/featured-section/{featuredSectionId}
     */
    @PutMapping("/featured-section/{featuredSectionId}")
    public BaseResponseEntity<Void> updateSection(
            @PathVariable UUID featuredSectionId,
            @RequestBody FeaturedSectionUpdateRequestDto featuredSectionUpdateRequestDto) {

        featuredSectionService.updateSection(featuredSectionId, featuredSectionUpdateRequestDto);
        return BaseResponseEntity.ok();
    }

    /**
     * ✅ 5. 추천 섹션 삭제
     * DELETE /api/featured-section/{featuredSectionId}
     */
    @DeleteMapping("/featured-section/{featuredSectionId}")
    public BaseResponseEntity<Void> deleteSection(@PathVariable UUID featuredSectionId) {
        featuredSectionService.deleteSection(featuredSectionId);
        return BaseResponseEntity.ok();
    }

    /**
     *  6. 추천 섹션에 상품 등록
     * POST /api/featured-section/regist-products
     */
    @PostMapping("/featured-section/regist-products")
    public BaseResponseEntity<Void> registerProductsToSection(
            @RequestBody FeaturedSectionProductRegisterRequestDto featuredSectionProductRegisterRequestDto) {

        featuredSectionService.registerProductsToSection(featuredSectionProductRegisterRequestDto);
        return BaseResponseEntity.ok();
    }
}
