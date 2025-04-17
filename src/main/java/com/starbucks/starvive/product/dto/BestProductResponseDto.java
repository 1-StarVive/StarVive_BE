package com.starbucks.starvive.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BestProductResponseDto {
    
    private UUID productId;
    private String nameKr;
    private String nameEn;
    private Integer price;
    private String thumbnailUrl; // 상품 대표 이미지 URL
    private Integer rank;        // 베스트 상품 순위

    @Builder
    public BestProductResponseDto(UUID productId, String nameKr, String nameEn, Integer price, String thumbnailUrl, Integer rank) {
        this.productId = productId;
        this.nameKr = nameKr;
        this.nameEn = nameEn;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.rank = rank;
    }
} 