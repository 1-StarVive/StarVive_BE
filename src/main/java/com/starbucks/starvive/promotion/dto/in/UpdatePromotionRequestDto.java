package com.starbucks.starvive.promotion.dto.in;

import com.starbucks.starvive.promotion.domain.PromotionStatus;
import com.starbucks.starvive.promotion.vo.UpdatePromotionVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UpdatePromotionRequestDto {

    private UUID promotionId;

    private String title;

    private String notice;

    private LocalDate promotionStartAt;

    private LocalDate promotionEndAt;

    private Boolean mainExpose;

    private String promotionDetailContent;

    private PromotionStatus promotionStatus;

    @Builder
    public UpdatePromotionRequestDto(UUID promotionId, String title, String notice,
                                     LocalDate promotionStartAt, LocalDate promotionEndAt,
                                     Boolean mainExpose, String promotionDetailContent,
                                     PromotionStatus promotionStatus) {
        this.promotionId = promotionId;
        this.title = title;
        this.notice = notice;
        this.promotionStartAt = promotionStartAt;
        this.promotionEndAt = promotionEndAt;
        this.mainExpose = mainExpose;
        this.promotionDetailContent = promotionDetailContent;
        this.promotionStatus = promotionStatus;
    }

    public static UpdatePromotionRequestDto from(UpdatePromotionVo updatePromotionRequestVo) {
        return UpdatePromotionRequestDto.builder()
                .promotionId(updatePromotionRequestVo.getPromotionId())
                .title(updatePromotionRequestVo.getTitle())
                .notice(updatePromotionRequestVo.getNotice())
                .promotionStartAt(updatePromotionRequestVo.getPromotionStartAt())
                .promotionEndAt(updatePromotionRequestVo.getPromotionEndAt())
                .mainExpose(updatePromotionRequestVo.getMainExpose())
                .promotionDetailContent(updatePromotionRequestVo.getPromotionDetailContent())
                .promotionStatus(updatePromotionRequestVo.getPromotionStatus())
                .build();
    }

}
