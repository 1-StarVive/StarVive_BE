package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String topCategoryId;

    @Builder
    public MiddleCategory(String name, String topCategoryId) {
        this.name = name;
        this.topCategoryId = topCategoryId;
    }
}
