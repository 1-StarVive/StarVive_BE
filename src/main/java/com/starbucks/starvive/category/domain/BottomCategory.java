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
public class BottomCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID bottomCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID middleCategoryId;

    @Builder
    public BottomCategory(UUID bottomCategoryId,
                          String name,
                          UUID middleCategoryId) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.middleCategoryId = middleCategoryId;
    }
}
