package com.starbucks.starvive.cart.dto.in;

import com.starbucks.starvive.cart.vo.DeleteSelectedCartItemsRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteSelectedCartItemsRequestDto {

    private List<UUID> cartItemIds;

    @Builder
    public DeleteSelectedCartItemsRequestDto(List<UUID> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    // VO → DTO 변환 메서드
    public static DeleteSelectedCartItemsRequestDto from(DeleteSelectedCartItemsRequestVo deleteSelectedCartItemsRequestVo) {
        return DeleteSelectedCartItemsRequestDto.builder()
                .cartItemIds(deleteSelectedCartItemsRequestVo.getCartItemIds())
                .build();
    }
}