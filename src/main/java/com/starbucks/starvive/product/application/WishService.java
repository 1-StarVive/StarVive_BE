package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.dto.out.WishToggleResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface WishService {

    void addWish(WishAddRequestDto wishAddRequestDto);
    WishToggleResponseDto toggle(WishAddRequestDto wishAddRequestDto);
    List<WishProductResponseDto> getList(UUID userId);
    void deleteWish(UUID wishId);

}