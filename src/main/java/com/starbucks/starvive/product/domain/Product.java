package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    private String name;

    private String code;

    private Double discountRate;

    private String shortDescription;

    private Boolean isAdditionalItem; // 추가 상품 여부

    private Boolean isEngraved; // 각인 여부

    private Boolean isTop; // 베스트 상품 여부

    private Boolean isNew; // 신규 상품 여부

    private boolean isLimitedEdition; // 한정 상품 여부

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;
}
