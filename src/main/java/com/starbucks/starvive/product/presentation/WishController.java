package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishService;
import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.dto.out.WishToggleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wish")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping
    public ResponseEntity<Void> addWish(@RequestBody WishAddRequestDto wishAddRequestDto) {
        wishService.addWish(wishAddRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle")
    public ResponseEntity<WishToggleResponseDto> toggleWishlist(@RequestBody WishAddRequestDto wishAddRequestDto) {
        return ResponseEntity.ok(wishService.toggle(wishAddRequestDto));
    }

    @GetMapping("/wishList")
    public ResponseEntity<List<WishProductResponseDto>> getWishlist(@RequestParam UUID userId) {
        return ResponseEntity.ok(wishService.getList(userId));
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<Void> deleteWish(@PathVariable UUID wishId) {
        wishService.deleteWish(wishId);
        return ResponseEntity.noContent().build();
    }
}
