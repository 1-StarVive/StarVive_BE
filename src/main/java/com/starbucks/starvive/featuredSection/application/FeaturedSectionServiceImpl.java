package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.featuredSection.domain.FeaturedSection;
import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.featuredSection.dto.in.*;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductGroupResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FeaturedSectionServiceImpl implements FeaturedSectionService {

    private final FeaturedSectionRepository sectionRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final FeaturedSectionProductRepository featuredSectionProductRepository;

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

    @Transactional(readOnly = true)
    @Override
    public List<FeaturedSectionProductGroupResponseDto> getProductsBySections(List<UUID> sectionIds) {

        List<FeaturedSectionProduct> sectionProducts = featuredSectionProductRepository.findAllByFeaturedSectionIdIn(sectionIds);

        Map<UUID, List<FeaturedSectionProductResponseDto>> groupedBySection = sectionProducts.stream()
                .collect(Collectors.groupingBy(
                        FeaturedSectionProduct::getFeaturedSectionId,
                        Collectors.mapping(featuredSectionProduct -> {

                            Product product = productRepository.findById(featuredSectionProduct.getProductId())
                                    .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));
                            ProductOption option = productOptionRepository.findById(featuredSectionProduct.getProductOptionId())
                                    .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));
                            ProductImage image = productImageRepository.findById(featuredSectionProduct.getProductImageId())
                                    .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

                            return FeaturedSectionProductResponseDto.from(featuredSectionProduct, product, option, image);
                        }, Collectors.toList())
                ));

        return groupedBySection.entrySet().stream()
                .map(entry -> FeaturedSectionProductGroupResponseDto.builder()
                        .featuredSectionsId(entry.getKey())
                        .products(entry.getValue())
                        .build())
                .toList();
    }

    @Override
    public void registerSingleProduct(AddFeaturedSectionSingleProductRequestDto dto) {
        FeaturedSectionProduct product = FeaturedSectionProduct.builder()
                .featuredSectionId(dto.getFeaturedSectionId())
                .productId(dto.getProductId())
                .productOptionId(dto.getProductOptionId())
                .productImageId(dto.getProductImageId())
                .build();
        featuredSectionProductRepository.save(product);
    }

    @Override
    public void registerProducts(AddFeaturedSectionProductRequestDto addFeaturedSectionProductRequestDto) {
        List<FeaturedSectionProduct> products = addFeaturedSectionProductRequestDto.getProductIds().stream()
                .map(productId -> {

                    UUID optionId = productOptionRepository.findFirstByProductId(productId)
                            .map(ProductOption::getProductOptionId)
                            .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));

                    UUID imageId = productImageRepository.findFirstByProductId(productId)
                            .map(ProductImage::getProductImageId)
                            .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

                    return FeaturedSectionProduct.builder()
                            .featuredSectionId(addFeaturedSectionProductRequestDto.getFeaturedSectionId())
                            .productId(productId)
                            .productOptionId(optionId)
                            .productImageId(imageId)
                            .build();
                })
                .toList();

        featuredSectionProductRepository.saveAll(products);
    }
}