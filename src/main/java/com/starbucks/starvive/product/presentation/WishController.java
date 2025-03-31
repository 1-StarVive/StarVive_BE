package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishService;
import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wish")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    /**
     * 상품 찜 추가 API
     * - 이미 찜한 상품이면 409 Conflict 발생
     */
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid WishAddRequestDto request) {
        wishService.addWishlist(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 찜한 상품 목록 조회 API
     * - productId로 상품 정보를 따로 조회하여 response DTO로 반환
     */
    @GetMapping("/list")
    public ResponseEntity<List<WishProductResponseDto>> getList(@RequestParam String userId) {
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
