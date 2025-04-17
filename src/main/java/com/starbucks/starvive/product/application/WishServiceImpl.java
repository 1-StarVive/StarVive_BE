package com.starbucks.starvive.product.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.dto.in.AddWishRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteWishRequestDto;
import com.starbucks.starvive.product.dto.in.ToggleWishRequestDto;
import com.starbucks.starvive.product.dto.out.WishListResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private static final String USER_LIKES_KEY_PREFIX = "user:";
    private static final String USER_LIKES_KEY_SUFFIX = ":likes";
    private static final String PRODUCT_LIKED_BY_KEY_PREFIX = "product:";
    private static final String PRODUCT_LIKED_BY_KEY_SUFFIX = ":liked_by";

    private final StringRedisTemplate redisTemplate;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public void addWish(AddWishRequestDto dto) {
        UUID userId = dto.getUserId();
        UUID productId = dto.getProductId();

        if (!productRepository.existsById(productId)) {
            throw new BaseException(NO_EXIST_PRODUCT);
        }

        String userLikesKey = USER_LIKES_KEY_PREFIX + userId + USER_LIKES_KEY_SUFFIX;
        String productLikedByKey = PRODUCT_LIKED_BY_KEY_PREFIX + productId + PRODUCT_LIKED_BY_KEY_SUFFIX;

        redisTemplate.opsForSet().add(userLikesKey, productId.toString());
        redisTemplate.opsForSet().add(productLikedByKey, userId.toString());

        log.info("찜 추가: userId={}, productId={}", userId, productId);
    }

    @Override
    public void deleteWish(DeleteWishRequestDto dto) {
        UUID userId = dto.getUserId();
        UUID productId = dto.getProductId();

        String userLikesKey = USER_LIKES_KEY_PREFIX + userId + USER_LIKES_KEY_SUFFIX;
        String productLikedByKey = PRODUCT_LIKED_BY_KEY_PREFIX + productId + PRODUCT_LIKED_BY_KEY_SUFFIX;

        redisTemplate.opsForSet().remove(userLikesKey, productId.toString());
        redisTemplate.opsForSet().remove(productLikedByKey, userId.toString());
        log.info("찜 삭제: userId={}, productId={}", userId, productId);
    }

    @Override
    public void toggleWish(ToggleWishRequestDto dto) {
        UUID userId = dto.getUserId();
        UUID productId = dto.getProductId();

        if (!productRepository.existsById(productId)) {
            throw new BaseException(NO_EXIST_PRODUCT);
        }

        String userLikesKey = USER_LIKES_KEY_PREFIX + userId + USER_LIKES_KEY_SUFFIX;
        String productLikedByKey = PRODUCT_LIKED_BY_KEY_PREFIX + productId + PRODUCT_LIKED_BY_KEY_SUFFIX;

        Boolean isMember = redisTemplate.opsForSet().isMember(userLikesKey, productId.toString());

        if (Boolean.TRUE.equals(isMember)) {
            redisTemplate.opsForSet().remove(userLikesKey, productId.toString());
            redisTemplate.opsForSet().remove(productLikedByKey, userId.toString());
            log.info("찜 해제 (토글): userId={}, productId={}", userId, productId);
        } else {
            redisTemplate.opsForSet().add(userLikesKey, productId.toString());
            redisTemplate.opsForSet().add(productLikedByKey, userId.toString());
            log.info("찜 추가 (토글): userId={}, productId={}", userId, productId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<WishListResponseDto> getWishList(UUID userId) {
        String userLikesKey = USER_LIKES_KEY_PREFIX + userId + USER_LIKES_KEY_SUFFIX;

        Set<String> likedProductIdsStr = redisTemplate.opsForSet().members(userLikesKey);
        if (likedProductIdsStr == null || likedProductIdsStr.isEmpty()) {
            return List.of();
        }

        List<UUID> likedProductIds = likedProductIdsStr.stream()
                .map(UUID::fromString)
                .toList();

        List<Product> likedProducts = productRepository.findAllById(likedProductIds);

        return likedProducts.stream()
                .map(product -> {
                    ProductImage image = productImageRepository.findFirstByProductId(product.getProductId()).orElse(null);
                    return WishListResponseDto.fromProduct(product, image);
                })
                .toList();
    }
}
