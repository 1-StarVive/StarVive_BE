package com.starbucks.starvive.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class ProductCategory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productCategoryId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId; // 상품 ID (Product 테이블과 연결)

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID categoryId; // 상위 카테고리 ID (Category 테이블과 연결)

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID filterCategoryId;  // 필터 카테고리 ID (FilterCategory 테이블과 연결)
}
