package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

//    @PostMapping("/wish")
//    public ResponseEntity<ErrorResponse<Void>> addWish(@RequestBody WishAddRequestDto wishAddRequestDto) {
//        wishService.addWish(wishAddRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
//
//    @PostMapping("/wish/toggle")
//    public ResponseEntity<ErrorResponse<WishToggleResponseDto>> toggleWishlist(
//            @RequestBody WishAddRequestDto wishAddRequestDto
//    ) {
//        WishToggleResponseDto result = wishService.toggle(wishAddRequestDto);
//        return ResponseEntity.ok(ErrorResponse.ok(result));
//    }
//
//    @GetMapping("/wish/wishList")
//    public ResponseEntity<ErrorResponse<List<WishProductResponseDto>>> getWishlist(@RequestParam UUID userId) {
//        List<WishProductResponseDto> result = wishService.getList(userId);
//        return ResponseEntity.ok(ErrorResponse.ok(result));
//    }
//
//    @DeleteMapping("/wish/{wishId}")
//    public ResponseEntity<ErrorResponse<Void>> deleteWish(@PathVariable UUID wishId) {
//        wishService.deleteWish(wishId);
//        return ResponseEntity.ok(ErrorResponse.ok());
//    }
}
