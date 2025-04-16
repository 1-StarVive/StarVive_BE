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

    private UUID userId;
    private List<UUID> cartItemIds;

    @Builder
    public DeleteSelectedCartItemsRequestDto(UUID userId, List<UUID> cartItemIds) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
    }

    // VO → DTO 변환 메서드
    public static DeleteSelectedCartItemsRequestDto from(DeleteSelectedCartItemsRequestVo deleteSelectedCartItemsRequestVo) {
        return DeleteSelectedCartItemsRequestDto.builder()
                .userId(deleteSelectedCartItemsRequestVo.getUserId())
                .cartItemIds(deleteSelectedCartItemsRequestVo.getCartItemIds())
                .build();
    }
}