package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ListPromotionResponseDto {

    private String title;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private String promotionDetailContent;

    @Builder
    public ListPromotionResponseDto(String title,
                                    LocalDate promotionStartAt, LocalDate promotionEndAt,
                                    String promotionDetailContent) {
        this.title = title;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.promotionDetailContent = promotionDetailContent;
    }

    public static ListPromotionResponseDto from(Promotion promotion) {
        return ListPromotionResponseDto.builder()
                .title(promotion.getTitle())
                .promotionStartAt(promotion.getPromotionStartAt())
                .promotionEndAt(promotion.getPromotionEndAt())
                .promotionDetailContent(promotion.getPromotionDetailContent())
                .build();
    }
}
