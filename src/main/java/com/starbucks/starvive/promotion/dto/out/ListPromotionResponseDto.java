package com.starbucks.starvive.promotion.dto.out;

import com.starbucks.starvive.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListPromotionResponseDto {

    private UUID promotionId;

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private String promotionDetailContent;

    @Builder
    public ListPromotionResponseDto(UUID promotionId, String title, String notice, LocalDate promotionStartAt,
                                    LocalDate promotionEndAt, String promotionDetailContent) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.promotionDetailContent = promotionDetailContent;
    }


    public static ListPromotionResponseDto from(Promotion promotion) {
        return ListPromotionResponseDto.builder()
                .promotionId(promotion.getPromotionId())
                .title(promotion.getTitle())
                .notice(promotion.getNotice())
                .promotionStartAt(promotion.getPromotionStartAt())
                .promotionEndAt(promotion.getPromotionEndAt())
                .promotionDetailContent(promotion.getPromotionDetailContent())
                .build();
    }
}
