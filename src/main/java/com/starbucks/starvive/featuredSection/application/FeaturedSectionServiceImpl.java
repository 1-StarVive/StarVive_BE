package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionCreateRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionProductRegisterRequestDto;
import com.starbucks.starvive.featuredSection.dto.in.FeaturedSectionUpdateRequestDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import com.starbucks.starvive.featuredSection.vo.FeaturedSectionProductVo;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeaturedSectionServiceImpl implements FeaturedSectionService {

    private final FeaturedSectionRepository featuredSectionRepository;
    private final FeaturedSectionProductRepository featuredSectionProductRepository;
    private final ProductOptionRepository productOptionRepository;


    @Override
    public List<FeaturedSectionResponseDto> getSectionList() {
        return featuredSectionRepository.findAllActivatedSections();
    }

    @Override
    public List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds) {
        List<FeaturedSectionProductVo> products = featuredSectionProductRepository.findProductsBySectionIds(sectionIds).stream()
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

    @Override
    public UUID createSection(FeaturedSectionCreateRequestDto featuredSectionCreateRequestDto) {
        FeaturedSection featuredSection = FeaturedSection.builder()
                .name(featuredSectionCreateRequestDto.getName())
                .activated(featuredSectionCreateRequestDto.isActivated())
                .build();
        return featuredSectionRepository.save(featuredSection).getFeaturedSectionId();
    }

    @Override
    public void updateSection(UUID featuredSectionId, FeaturedSectionUpdateRequestDto featuredSectionUpdateRequestDto) {
        FeaturedSection section = featuredSectionRepository.findById(featuredSectionId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_FEATURED_SECTION));;
        section.update(featuredSectionUpdateRequestDto.getName(), featuredSectionUpdateRequestDto.isActivated());
    }

    @Override
    public void deleteSection(UUID featuredSectionId) {
        featuredSectionRepository.deleteById(featuredSectionId);
    }

    @Override
    public void registerProductsToSection(FeaturedSectionProductRegisterRequestDto featuredSectionProductRegisterRequestDto) {
        List<FeaturedSectionProduct> entities = featuredSectionProductRegisterRequestDto.getProducts().stream()
                .map(p -> FeaturedSectionProduct.builder()
                        .featuredSectionId(featuredSectionProductRegisterRequestDto.getFeaturedSectionId())
                        .productId(p.getProductId())
                        .productOptionId(p.getProductOptionId())
                        .productImageId(p.getProductImageId())
                        .build())
                .toList();

        featuredSectionProductRepository.saveAll(entities);
    }

}
