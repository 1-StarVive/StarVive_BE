package com.starbucks.starvive.product.vo;

import com.starbucks.starvive.product.dto.BestProductResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BestProductResponseVo {
    private UUID productId;
    private String nameKr;
    private String nameEn;
    private Integer price;
    private String thumbnailUrl;
    private Integer rank;

    @Builder
    private BestProductResponseVo(UUID productId, String nameKr, String nameEn, Integer price, String thumbnailUrl, Integer rank) {
        this.productId = productId;
        this.nameKr = nameKr;
        this.nameEn = nameEn;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.rank = rank;
    }

    // DTO to VO conversion method
    public static BestProductResponseVo from(BestProductResponseDto dto) {
        return BestProductResponseVo.builder()
                .productId(dto.getProductId())
                .nameKr(dto.getNameKr())
                .nameEn(dto.getNameEn())
                .price(dto.getPrice())
                .thumbnailUrl(dto.getThumbnailUrl())
                .rank(dto.getRank())
                .build();
    }
} 