package com.starbucks.starvive.product.application;

import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.in.ProductCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductImageCreateRequestDto;
import com.starbucks.starvive.product.dto.in.ProductOptionCreateRequestDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import com.starbucks.starvive.product.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository imageRepository;

    @Override
    public UUID createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product product = productRepository.save(Product.builder()
                .name(productCreateRequestDto.getName())
                .description(productCreateRequestDto.getDescription())
                .baseDiscountRate(productCreateRequestDto.getBaseDiscountRate())
                .productStatus(productCreateRequestDto.getProductStatus())
                .build());
        return product.getProductId();
    }

    @Override
    public UUID createProductOption(ProductOptionCreateRequestDto ProductOptionCreateRequestDto) {
        ProductOption option = optionRepository.save(ProductOption.builder()
                .productId(ProductOptionCreateRequestDto.getProductId())
                .price(ProductOptionCreateRequestDto.getPrice())
                .stock(ProductOptionCreateRequestDto.getStock())
                .carvedAvailable(ProductOptionCreateRequestDto.getCarvedAvailable())
                .colorId(ProductOptionCreateRequestDto.getColorId())
                .sizeId(ProductOptionCreateRequestDto.getSizeId())
                .build());
        return option.getProductOptionId();
    }

    @Override
    public UUID createProductImage(ProductImageCreateRequestDto productImageCreateRequestDto) {
        ProductImage image = imageRepository.save(ProductImage.builder()
                .productId(productImageCreateRequestDto.getProductId())
                .imageThumbUrl(productImageCreateRequestDto.getImageThumbUrl())
                .imageThumbAlt(productImageCreateRequestDto.getImageThumbAlt())
                .build());
        return image.getProductImageId();
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAllWithOptionAndImage().stream()
                .map(ProductVo::new)
                .map(ProductVo::toDto)
                .toList();
    }
    @Override
    public ProductResponseDto getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .baseDiscountRate(product.getBaseDiscountRate())
                .productStatus(product.getProductStatus())
                .build();
    }

    @Override
    public void updateProduct(UUID productId, ProductCreateRequestDto productCreateRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        productRepository.save(Product.builder()
                .productId(productId)
                .name(productCreateRequestDto.getName())
                .description(productCreateRequestDto.getDescription())
                .baseDiscountRate(productCreateRequestDto.getBaseDiscountRate())
                .productStatus(productCreateRequestDto.getProductStatus())
                .build());
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
