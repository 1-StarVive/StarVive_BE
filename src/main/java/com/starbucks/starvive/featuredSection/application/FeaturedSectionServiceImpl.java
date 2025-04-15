package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.featuredSection.dto.in.*;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.NO_EXIST_SECTION;

@Service
@RequiredArgsConstructor
public class FeaturedSectionServiceImpl implements FeaturedSectionService {

    private final FeaturedSectionRepository sectionRepository;
    private final FeaturedSectionProductRepository productRepository;

    @Override
    public FeaturedSection getSection(UUID sectionId) {
        return sectionRepository.findByFeaturedSectionId(sectionId)
                .orElseThrow(() -> new BaseException(NO_EXIST_SECTION));
    }

    @Override
    public List<FeaturedSection> getSectionList() {
        return sectionRepository.findAll();
    }


    @Override
    public void createSection(AddFeaturedSectionRequestDto dto) {
        FeaturedSection section = FeaturedSection.builder()
                .name(dto.getName())
                .activated(dto.isActivated())
                .build();
        sectionRepository.save(section);
    }

    @Override
    public void updateSection(UpdateFeaturedSectionRequestDto updateFeaturedSectionRequestDto) {
        FeaturedSection section = sectionRepository.findByFeaturedSectionId(updateFeaturedSectionRequestDto.getFeaturedSectionId())
                .orElseThrow(() -> new BaseException(NO_EXIST_SECTION));
        section.update(updateFeaturedSectionRequestDto.getName(), updateFeaturedSectionRequestDto.isActivated());
    }

    @Override
    public void deleteSection(DeleteFeaturedSectionRequestDto dto) {
        sectionRepository.deleteById(dto.getFeaturedSectionId());
    }

    @Override
    public List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds) {
        List<FeaturedSectionProduct> list = productRepository.findAllByFeaturedSectionIdIn(sectionIds);
        // 임시: 실제 응답 DTO 가공 로직은 별도 정의 필요
        return new ArrayList<>();
    }

    @Override
    public void registerSingleProduct(AddFeaturedSectionSingleProductRequestDto dto) {
        FeaturedSectionProduct product = FeaturedSectionProduct.builder()
                .featuredSectionId(dto.getFeaturedSectionId())
                .productId(dto.getProductId())
                .productOptionId(dto.getProductOptionId())
                .productImageId(dto.getProductImageId())
                .build();
        productRepository.save(product);
    }

    @Override
    public void registerProducts(AddFeaturedSectionProductRequestDto dto) {
        List<FeaturedSectionProduct> products = dto.getProductIds().stream()
                .map(pid -> FeaturedSectionProduct.builder()
                        .featuredSectionId(dto.getFeaturedSectionId())
                        .productId(pid)
                        .productOptionId(null)
                        .productImageId(null)
                        .build())
                .toList();
        productRepository.saveAll(products);
    }
}
