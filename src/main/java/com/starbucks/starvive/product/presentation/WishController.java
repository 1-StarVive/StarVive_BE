package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishService;
import com.starbucks.starvive.product.dto.in.AddWishRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteWishRequestDto;
import com.starbucks.starvive.product.dto.in.ToggleWishRequestDto;
import com.starbucks.starvive.product.dto.out.WishListResponseDto;
import com.starbucks.starvive.product.dto.out.WishStatusResponseDto;
import com.starbucks.starvive.product.vo.AddWishRequestVo;
import com.starbucks.starvive.product.vo.DeleteWishRequestVo;
import com.starbucks.starvive.product.vo.ToggleWishRequestVo;
import com.starbucks.starvive.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/wish")
@RestController
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping
    public void addWish(@AuthenticationPrincipal User user, @RequestBody AddWishRequestVo addWishRequestVo) {

        wishService.addWish(AddWishRequestDto.from(addWishRequestVo, user.getUserId()));
    }

    @GetMapping
    public List<WishListResponseDto> getWishList(@AuthenticationPrincipal User user) {

        return wishService.getWishList(user.getUserId());
    }

    @DeleteMapping
    public void deleteWish(@AuthenticationPrincipal User user, @RequestBody DeleteWishRequestVo deleteWishRequestVo) {

        wishService.deleteWish(DeleteWishRequestDto.from(deleteWishRequestVo, user.getUserId()));
    }

    //@PostMapping("/toggle")
    // public void toggleWish(
    //         @AuthenticationPrincipal User user,
    //         @RequestBody ToggleWishRequestVo toggleWishRequestVo
    // ) {
    //     ToggleWishRequestDto requestDto = ToggleWishRequestDto.from(toggleWishRequestVo, user.getUserId());
    //     wishService.toggleWish(requestDto);
    // }

    @GetMapping("/status")
    public WishStatusResponseDto getWishStatus(@AuthenticationPrincipal User user, @RequestParam UUID productId) {
        
        return new WishStatusResponseDto(productId, wishService.isWished(user.getUserId(), productId));
    }
}
