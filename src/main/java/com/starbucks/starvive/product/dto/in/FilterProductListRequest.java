package com.starbucks.starvive.product.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FilterProductListRequest {

    private UUID topCategoryId;

    private List<String> categoriesNames;   // 중간 카테고리 '카테고리' 하위 필터들

    private List<String> seasonNames;       // 중간 카테고리 '시즌' 하위 필터들

    private List<String> sizeNames;         // 중간 카테고리 '용량' 하위 필터들

    private String priceName;               // 중간 카테고리 '가격' 하위 필터 (단일)

    private UUID lastProductId;

    private Integer pageSize;

    @Builder
    public FilterProductListRequest(UUID topCategoryId, List<String> categoriesNames,
                                    List<String> seasonNames, List<String> sizeNames,
                                    String priceName, UUID lastProductId, Integer pageSize) {
        this.topCategoryId = topCategoryId;
        this.categoriesNames = categoriesNames;
        this.seasonNames = seasonNames;
        this.sizeNames = sizeNames;
        this.priceName = priceName;
        this.lastProductId = lastProductId;
        this.pageSize = pageSize;
    }
}
