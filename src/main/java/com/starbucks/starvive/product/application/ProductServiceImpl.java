package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.exception.BaseException;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository imageRepository;

    @Override
    public UUID createProduct(ProductCreateRequestDto productCreateRequestDto) {
               Product product = productRepository.save(Product.builder()
                .name(productCreateRequestDto.getName())
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .productStatus(product.getProductStatus())
                .build();
    }
    @Transactional
    @Override
    public void updateProduct(UUID productId, ProductCreateRequestDto productCreateRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.update(
                productCreateRequestDto.getName(),
                productCreateRequestDto.getProductStatus()
        );
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
