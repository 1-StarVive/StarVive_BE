package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.domain.Wish;
import com.starbucks.starvive.product.dto.in.WishAddRequestDto;
import com.starbucks.starvive.product.dto.out.WishProductResponseDto;
import com.starbucks.starvive.product.dto.out.WishToggleResponseDto;
import com.starbucks.starvive.product.infrastructure.WishRepository;
import com.starbucks.starvive.product.vo.WishVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;

    @Override
    public void addWish(WishAddRequestDto wishAddRequestDto) {
        Wish wish = Wish.create(wishAddRequestDto.getUserId(), wishAddRequestDto.getProductId());
        wishRepository.save(wish);
    }

    @Override
    public WishToggleResponseDto toggle(WishAddRequestDto request) {
        UUID userId = request.getUserId();
        UUID productId = request.getProductId();

        Optional<Wish> existing = wishRepository.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            wishRepository.delete(existing.get());
            return new WishToggleResponseDto(productId, false);
        }

        Wish wishlist = Wish.create(userId, productId);
        wishRepository.save(wishlist);
        return new WishToggleResponseDto(productId, true);
    }


    @Override
    public List<WishProductResponseDto> getList(UUID userId) {
        return wishRepository.findByUserId(userId).stream()
                .map(WishVo::new)
                .map(WishVo::toDto)
                .toList();
    }

    @Override
    public void deleteWish(UUID wishId) {
        wishRepository.deleteById(wishId);
    }
}
