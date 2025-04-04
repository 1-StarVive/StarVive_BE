package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishRankingService;
import com.starbucks.starvive.product.application.WishService;
import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.infrastructure.WishRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wish")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;
    private final WishRankingService wishRankingService;

    /**
     * 상품 찜 추가 API
     * - 이미 찜한 상품이면 409 Conflict 발생
     *
     *   @PostMapping
     *     public ResponseEntity<Void> addWish(@RequestBody @Valid WishAddRequestDto request) {
     *         wishService.addWish(request);
     *         return ResponseEntity.status(HttpStatus.CREATED).build();
     *     }
     *
     *
     */
    /**
     * 🔼 상품 찜 추가 API (Redis ZINCRBY)
     * - 사용자가 상품을 찜할 때 호출됨
     * - Redis ZSet에 productId의 점수를 +1 증가시킴
     * - 추후 Spring Batch에서 이 데이터를 기반으로 베스트 상품 선정
     */

    @PostMapping("/add")
    public ResponseEntity<Void> addWish(@RequestParam UUID productId) {
        wishRankingService.incrementWish(productId);
        return ResponseEntity.ok().build();
    }



    /**
     * 찜한 상품 목록 조회 API
     * - productId로 상품 정보를 따로 조회하여 response DTO로 반환
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProductListResponseDto>> getList(@RequestParam UUID userId) {
        return ResponseEntity.ok(wishService.getWishByUser(userId));
    }

    /**
     * 찜 삭제 API
     */
    @DeleteMapping("/{wishId}")
    public ResponseEntity<Void> deleteWish(@PathVariable UUID wishId) {
        wishService.deleteWish(wishId);
        return ResponseEntity.noContent().build();
    }
}
