package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.vo.DeletePromotionVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeletePromotionRequestDto {

    private UUID promotionId;

    @Builder
    public DeletePromotionRequestDto(UUID promotionId) {
        this.promotionId = promotionId;
    }

    public static DeletePromotionRequestDto from(DeletePromotionVo deletePromotionRequestVo) {
        return DeletePromotionRequestDto.builder()
                .promotionId(deletePromotionRequestVo.getPromotionId())
                .build();
    }
}
