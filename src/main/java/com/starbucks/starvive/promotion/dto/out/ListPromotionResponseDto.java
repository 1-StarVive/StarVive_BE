package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListPromotionResponseDto {

    private UUID promotionId;

    private String title;

    private String promotionDetailContent;

    @Builder
    public ListPromotionResponseDto(UUID promotionId, String title,
                                    String promotionDetailContent) {
        this.promotionId = promotionId;
        this.title = title;
        this.promotionDetailContent = promotionDetailContent;
    }

    public static ListPromotionResponseDto from(Promotion promotion) {
        return ListPromotionResponseDto.builder()
                .promotionId(promotion.getPromotionId())
                .title(promotion.getTitle())
                .promotionDetailContent(promotion.getPromotionDetailContent())
                .build();
    }
}
