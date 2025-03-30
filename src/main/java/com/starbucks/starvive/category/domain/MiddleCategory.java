package com.starbucks.starvive.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID middleCategoryId;

    @Column(nullable = false)
    private String name;

    private String topCategoryId;

    @Builder
    public MiddleCategory(UUID middleCategoryId,
                          String name, String topCategoryId) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
    }
}
