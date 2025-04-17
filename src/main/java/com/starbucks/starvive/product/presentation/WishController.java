package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.product.application.WishService;
import com.starbucks.starvive.product.dto.in.AddWishRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteWishRequestDto;
import com.starbucks.starvive.product.dto.in.ToggleWishRequestDto;
import com.starbucks.starvive.product.dto.out.WishListResponseDto;
import com.starbucks.starvive.product.vo.AddWishRequestVo;
import com.starbucks.starvive.product.vo.DeleteWishRequestVo;
import com.starbucks.starvive.product.vo.ToggleWishRequestVo;
import com.starbucks.starvive.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/v1/wish")
@RestController
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @Operation(summary = "찜 추가", description = "찜을 등록합니다.", tags = {"wish"})
    @PostMapping("/add")
    public void addWish(
            @AuthenticationPrincipal User user,
            @RequestBody AddWishRequestVo addWishRequestVo
    ) {
        AddWishRequestDto requestDto = AddWishRequestDto.from(addWishRequestVo, user.getUserId());
        wishService.addWish(requestDto);
    }

    @Operation(summary = "찜 토글", description = "찜 상태를 토글합니다.", tags = {"wish"})
    @PostMapping("/toggle")
    public void toggleWish(
            @AuthenticationPrincipal User user,
            @RequestBody ToggleWishRequestVo toggleWishRequestVo
    ) {
        ToggleWishRequestDto requestDto = ToggleWishRequestDto.from(toggleWishRequestVo, user.getUserId());
        wishService.toggleWish(requestDto);
    }

    @Operation(summary = "찜 목록 조회", description = "사용자의 찜 목록을 조회합니다.", tags = {"wish"})
    @GetMapping("/all")
    public List<WishListResponseDto> getWishList(
            @AuthenticationPrincipal User user
    ) {
        return wishService.getWishList(user.getUserId());
    }

    @Operation(summary = "찜 삭제", description = "찜을 삭제합니다.", tags = {"wish"})
    @DeleteMapping
    public void deleteWish(
            @AuthenticationPrincipal User user,
            @RequestBody DeleteWishRequestVo deleteWishRequestVo
    ) {
        DeleteWishRequestDto requestDto = DeleteWishRequestDto.from(deleteWishRequestVo, user.getUserId());
        wishService.deleteWish(requestDto);
    }
}
