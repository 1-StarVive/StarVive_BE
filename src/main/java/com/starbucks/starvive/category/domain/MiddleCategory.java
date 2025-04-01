package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID middleCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID topCategoryId;

    @Builder
    public MiddleCategory(String name, UUID topCategoryId) {
        this.middleCategoryId = UUID.randomUUID();
        this.name = name;
        this.topCategoryId = topCategoryId;
    }
}
