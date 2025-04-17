package com.starbucks.starvive.featuredSection.presentation;

import com.starbucks.starvive.featuredSection.application.FeaturedSectionService;
import com.starbucks.starvive.featuredSection.dto.in.*;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/featured-section")
@RequiredArgsConstructor
public class FeaturedSectionController {

    private final FeaturedSectionService featuredSectionService;

    @Operation(summary = "추천 섹션 단건 조회", description = "추천 섹션 ID로 단건을 조회합니다.", tags = {"featured-section"})
    @GetMapping
    public FeaturedSectionResponseVo getSection(@RequestParam("featuredSectionId") UUID featuredSectionId) {
        return FeaturedSectionResponseVo.from(featuredSectionService.getSection(featuredSectionId));
    }

    @Operation(summary = "추천 섹션 전체 조회", description = "모든 추천 섹션을 조회합니다.", tags = {"featured-section"})
    @GetMapping("/all")
    public List<FeaturedSectionResponseVo> getAllSections() {
        return featuredSectionService.getSectionList().stream()
                .map(FeaturedSectionResponseVo::from)
                .toList();
    }

    @Operation(summary = "추천 섹션 등록", description = "추천 섹션을 등록합니다.", tags = {"featured-section"})
    @PostMapping("/add")
    public void createSection(@RequestBody AddFeaturedSectionRequestVo addFeaturedSectionRequestVo) {
        featuredSectionService.createSection(AddFeaturedSectionRequestDto.from(addFeaturedSectionRequestVo));
    }

    @Operation(summary = "추천 섹션 수정", description = "추천 섹션을 수정합니다.", tags = {"featured-section"})
    @PutMapping
    public void updateSection(@RequestBody UpdateFeaturedSectionRequestVo updateFeaturedSectionRequestVo) {
        featuredSectionService.updateSection(UpdateFeaturedSectionRequestDto.from(updateFeaturedSectionRequestVo));
    }

    @Operation(summary = "추천 섹션 삭제", description = "추천 섹션을 삭제합니다.", tags = {"featured-section"})
    @DeleteMapping
    public void deleteSection(@RequestBody DeleteFeaturedSectionRequestVo deleteFeaturedSectionRequestVo) {
        featuredSectionService.deleteSection(DeleteFeaturedSectionRequestDto.from(deleteFeaturedSectionRequestVo));
    }

    @Operation(summary = "추천 섹션 상품 리스트 조회", description = "추천 섹션 ID 리스트로 섹션별 상품을 조회합니다.", tags = {"featured-section"})
    @GetMapping("/products")
    public List<FeaturedSectionProductGroupResponseDto> getProductsBySection(
            @RequestParam("featuredSectionIds") List<UUID> featuredSectionIds) {
        return featuredSectionService.getProductsBySections(featuredSectionIds);
    }

    @Operation(summary = "추천 섹션에 상품 단건 등록", description = "추천 섹션에 하나의 상품을 등록합니다.", tags = {"featured-section"})
    @PostMapping("/register-product")
    public void registerSingleProduct(@RequestBody AddFeaturedSectionSingleProductRequestVo addFeaturedSectionSingleProductRequestVo) {
        featuredSectionService.registerSingleProduct(AddFeaturedSectionSingleProductRequestDto.from(addFeaturedSectionSingleProductRequestVo));
    }

    @Operation(summary = "추천 섹션에 상품 일괄 등록", description = "추천 섹션에 여러 상품을 한 번에 등록합니다.", tags = {"featured-section"})
    @PostMapping("/register-products")
    public void registerMultipleProducts(@RequestBody AddFeaturedSectionProductRequestVo addFeaturedSectionProductRequestVo) {
        featuredSectionService.registerProducts(AddFeaturedSectionProductRequestDto.from(addFeaturedSectionProductRequestVo));
    }
}
