package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.domain.Wish;
import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import com.starbucks.starvive.product.infrastructure.WishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    // 상품 찜 추가
    public void addWishlist(WishAddRequestDto request) {
        wishRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .ifPresent(w -> { throw new IllegalStateException("이미 찜한 상품입니다."); });

        Wish wish = Wish.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .build();

        wishRepository.save(wish);
    }

    // 찜 목록 조회 (상품 정보 포함)
    public List<WishProductResponseDto> getWishByUser(String userId) {
        List<Wish> wishList = wishRepository.findByUserId(userId);
        List<String> productIds = wishList.stream()
                .map(Wish::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);
        Map<UUID, Product> productMap = products.stream() //타입
                .collect(Collectors.toMap(Product::getProductId, Function.identity()));

        return wishList.stream()
                .map(w -> WishProductResponseDto.from(productMap.get(w.getProductId())))
                .collect(Collectors.toList());
    }

    // 찜 삭제
    public void deleteWish(UUID wishId) {
        if (!wishRepository.existsById(wishId)) {
            throw new EntityNotFoundException("해당 찜 항목이 존재하지 않습니다.");
        }
        wishRepository.deleteById(wishId);
    }
}
