package com.starbucks.starvive.product.dto.in;

import com.starbucks.starvive.product.vo.DeleteWishRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteWishRequestDto {
    private UUID wishId;

    @Builder
    public DeleteWishRequestDto(UUID wishId) {
        this.wishId = wishId;
    }

    public static DeleteWishRequestDto from(DeleteWishRequestVo deleteWishRequestVo) {
        return DeleteWishRequestDto.builder()
                .wishId(deleteWishRequestVo.getWishId())
                .build();
    }
}
