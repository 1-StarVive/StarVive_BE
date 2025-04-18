package com.starbucks.starvive.product.application;

import com.amazonaws.services.kms.model.NotFoundException;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductDetailImage;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.in.AddProductRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteProductRequestDto;
import com.starbucks.starvive.product.dto.in.UpdateProductRequestDto;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;
import com.starbucks.starvive.product.dto.out.ProductResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import com.starbucks.starvive.product.infrastructure.ProductRequiredInfoRepository;
import com.starbucks.starvive.product.infrastructure.productDetailImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final productDetailImageRepository productDetailImageRepository;
    private final ProductRequiredInfoRepository productRequiredInfoRepository;

    @Override
    public void addProduct(AddProductRequestDto addProductRequestDto) {
        Product product = Product.builder()
                .name(addProductRequestDto.getName())
                .productStatus(addProductRequestDto.getProductStatus())
                .build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(UpdateProductRequestDto updateProductRequestDto) {
        Product product = productRepository.findByProductId(updateProductRequestDto.getProductId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));
        product.update(updateProductRequestDto.getName(), updateProductRequestDto.getProductStatus());
    }

    @Override
    public void deleteProduct(DeleteProductRequestDto deleteProductRequestDto) {
        Product product = productRepository.findByProductId(deleteProductRequestDto.getProductId())
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto getProduct(UUID productId) {
        return ProductResponseDto.from(productRepository.findByProductId(productId)
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductListResponseDto> getProductsByCursor(UUID lastProductId, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Product> products = (lastProductId == null)
                ? productRepository.findAllByOrderByProductIdDesc(pageable)
                : productRepository.findByProductIdLessThanOrderByProductIdDesc(lastProductId, pageable);

        return products.stream()
                .map(product -> {
                    ProductOption option = productOptionRepository.findFirstByProductId(product.getProductId()).orElse(null);
                    ProductImage image = productImageRepository.findFirstByProductId(product.getProductId()).orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));
                    return ProductListResponseDto.from(product, option, image);
                })
                .toList();
    }

    @Override
    public ProductDetailResponseDto getProductDetail(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));

        ProductDetailImage detailImage = productDetailImageRepository.findByProductId(productId)
                .orElse(null);

        List<ProductRequiredInfoResponseDto> requiredInfos = productRequiredInfoRepository.findByProductId(productId)
                .stream()
                .map(ProductRequiredInfoResponseDto::from)
                .collect(Collectors.toList());

        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .productStatus(product.getProductStatus())
                .productDetailContent(detailImage != null ? detailImage.getProductDetailContent() : null)
                .requiredInfos(requiredInfos)
                .build();
    }
}

