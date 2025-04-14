package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.domain.Wish;
import com.starbucks.starvive.product.dto.in.AddWishRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteWishRequestDto;
import com.starbucks.starvive.product.dto.in.ToggleWishRequestDto;
import com.starbucks.starvive.product.dto.out.WishListResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import com.starbucks.starvive.product.infrastructure.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    @Override
    public void addWish(AddWishRequestDto dto) {
        if (wishRepository.findByUserIdAndProductOptionId(dto.getUserId(), dto.getProductOptionId()).isPresent()) {
            throw new BaseException(DUPLICATED_WISH);
        }
        Wish wish = Wish.builder()
                .userId(dto.getUserId())
                .productOptionId(dto.getProductOptionId())
                .build();
        wishRepository.save(wish);
    }

    @Transactional
    @Override
    public void deleteWish(DeleteWishRequestDto dto) {
        wishRepository.deleteById(dto.getWishId());
    }

    @Transactional
    @Override
    public void toggleWish(ToggleWishRequestDto dto) {
        wishRepository.findByUserIdAndProductOptionId(dto.getUserId(), dto.getProductOptionId())
                .ifPresentOrElse(
                        wish -> wishRepository.deleteById(wish.getWishId()),
                        () -> wishRepository.save(Wish.builder()
                                .userId(dto.getUserId())
                                .productOptionId(dto.getProductOptionId())
                                .build()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<WishListResponseDto> getWishList(UUID userId) {
        List<Wish> wishes = wishRepository.findAllByUserId(userId);
        return wishes.stream().map(wish -> {
            ProductOption option = productOptionRepository.findById(wish.getProductOptionId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_OPTION));

            Product product = productRepository.findById(option.getProductId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_PRODUCT));

            ProductImage image = productImageRepository.findFirstByProductId(product.getProductId())
                    .orElseThrow(() -> new BaseException(NO_EXIST_IMAGE));

            return WishListResponseDto.from(wish, option, product, image);

        }).toList();
    }
}
