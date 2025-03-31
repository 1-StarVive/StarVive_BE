package com.starbucks.starvive.featuredSection.application;

import com.starbucks.starvive.featuredSection.domain.FeaturedSectionProduct;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionProductResponseDto;
import com.starbucks.starvive.featuredSection.dto.out.FeaturedSectionResponseDto;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionProductRepository;
import com.starbucks.starvive.featuredSection.infrastructure.FeaturedSectionRepository;
import com.starbucks.starvive.featuredSection.vo.FeaturedSectionProductVo;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FeaturedSectionService {

    private final FeaturedSectionRepository sectionRepository;
    private final FeaturedSectionProductRepository sectionProductRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    /**
     *  [GET] 추천 섹션 리스트 조회
     * - 섹션 ID와 이름만 반환
     * - 상품 정보는 포함하지 않음
     */
    public List<FeaturedSectionResponseDto> getOnlySections() {
        return sectionRepository.findByActivatedTrue().stream()
                .map(section -> new FeaturedSectionResponseDto(
                        section.getFeaturedSectionId().toString(),
                        section.getName()
                ))
                .toList();
    }

    /**
     *   [POST] 추천 섹션 상품 리스트 조회
     * - 요청한 섹션 ID 목록에 대해
     * - 각 섹션에 포함된 추천 상품을 구성해서 반환
     */
    public List<FeaturedSectionProductResponseDto> getSectionsByIds(List<String> sectionIds) {
        List<UUID> ids = sectionIds.stream().map(UUID::fromString).toList();

        return sectionRepository.findAllById(ids).stream()
                .map(section -> {
                    List<FeaturedSectionProduct> sectionProducts =
                            sectionProductRepository.findByFeaturedSection(section);

                    List<FeaturedSectionProductVo> products = sectionProducts.stream().map(fsp -> {
                        UUID productId = UUID.fromString(fsp.getProductId());

                        Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                        //ProductOption option = productOptionRepository.findByProductId(productId)
                         //       .orElseThrow(() -> new RuntimeException("ProductOption not found"));
                      //  ProductImage image = productImageRepository.findThumbnailByProductId(productId)
                      //          .orElseThrow(() -> new RuntimeException("Thumbnail image not found"));

                      //  int discountedPrice = product.getPrice() * (100 - option.getDiscountRate()) / 100;

                        return FeaturedSectionProductVo.builder()
                                .productId(UUID.fromString(productId.toString()))
                         //       .url(image.getThumbnailUrl())
                        //        .alt(image.getThumbnailAlt())
                                .name(product.getName())
                         //       .price(option.getPrice())
                         //       .discountRate(product.getDiscountRate())
                          //      .discountedPrice(discountedPrice)
                            //     .code(product.getCode())
                           //     .LimitedEdition(option.LimitedEdition())
                          //      .topProduct(product.TopProduct())
                           //     .newProduct(product.NewProduct())
                                .build();
                    }).toList();

                    return FeaturedSectionProductResponseDto.builder()
                           .featuredSectionsId(section.getFeaturedSectionId().toString())
                           .products(products)
                           .build();
               })
               .toList();
    }
}