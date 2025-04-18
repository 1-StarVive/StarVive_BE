package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.AddWishRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteWishRequestDto;
import com.starbucks.starvive.product.dto.in.ToggleWishRequestDto;
import com.starbucks.starvive.product.dto.out.WishListResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface WishService {

    void addWish(AddWishRequestDto addWishRequestDto);

    void toggleWish(ToggleWishRequestDto toggleWishRequestDto);

    void deleteWish(DeleteWishRequestDto deleteWishRequestDto);
    
    List<WishListResponseDto> getWishList(UUID userId);
}