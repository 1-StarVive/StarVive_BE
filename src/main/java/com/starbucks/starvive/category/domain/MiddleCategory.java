package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.category.dto.in.UpdateMiddleCategoryRequestDto;
import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
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

    private UUID topCategoryId;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted = false;

    @Builder
    public MiddleCategory(UUID middleCategoryId, String name, UUID topCategoryId, boolean deleted) {
        this.middleCategoryId = middleCategoryId;
        this.name = name;
        this.topCategoryId = topCategoryId;
        this.deleted = deleted;
    }

    public void update(UpdateMiddleCategoryRequestDto updateMiddleCategoryRequestDto) {
        this.name = updateMiddleCategoryRequestDto.getName();
    }

    public void softDelete() {
        this.deleted = true;
    }
}
