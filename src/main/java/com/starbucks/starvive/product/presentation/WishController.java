package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping("/wish")
    public BaseResponseEntity<Void> addWish(@RequestBody WishAddRequestDto wishAddRequestDto) {
        wishService.addWish(wishAddRequestDto);
        return BaseResponseEntity.ok();
    }

    @PostMapping("/wish/toggle")
    public BaseResponseEntity<WishToggleResponseDto> toggleWishlist(
            @RequestBody WishAddRequestDto wishAddRequestDto) {
        WishToggleResponseDto result = wishService.toggle(wishAddRequestDto);
        return BaseResponseEntity.ok(result);
    }

    @GetMapping("/wish/wishList")
    public BaseResponseEntity<List<WishProductResponseDto>> getWishlist(@RequestParam UUID userId) {
        List<WishProductResponseDto> result = wishService.getList(userId);
        return BaseResponseEntity.ok(result);
    }

    @DeleteMapping("/wish/{wishId}")
    public BaseResponseEntity<Void> deleteWish(@PathVariable UUID wishId) {
        wishService.deleteWish(wishId);
        return BaseResponseEntity.ok();
    }
}
