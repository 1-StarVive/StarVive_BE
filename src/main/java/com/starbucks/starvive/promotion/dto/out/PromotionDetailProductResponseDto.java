package com.starbucks.starvive.promotion.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PromotionDetailProductResponseDto {

    private UUID promotionId;

    private String title;

    private String notice;

    private String promotionDetailContent;

    private List<PromotionProductResponseDto> products;

    @Builder
    public PromotionDetailProductResponseDto(UUID promotionId, String title, String notice,
                                             String promotionDetailContent,
                                             List<PromotionProductResponseDto> products) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionDetailContent = promotionDetailContent;
        this.products = products;
    }
}
