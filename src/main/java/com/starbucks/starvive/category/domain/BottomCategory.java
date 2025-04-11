package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.category.dto.in.BottomCategoryRequest;
import com.starbucks.starvive.category.dto.in.UpdateBottomCategoryRequestDto;
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
public class BottomCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID bottomCategoryId;

    @Column(nullable = false)
    private String name;

    private UUID middleCategoryId;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean deleted = false;

    @Builder
    public BottomCategory(UUID bottomCategoryId, String name,
                          UUID middleCategoryId, boolean deleted) {
        this.bottomCategoryId = bottomCategoryId;
        this.name = name;
        this.middleCategoryId = middleCategoryId;
        this.deleted = deleted;
    }

    public void update(UpdateBottomCategoryRequestDto updateBottomCategoryRequestDto) {
        this.name = updateBottomCategoryRequestDto.getName();
    }

    public void softDelete() {
        this.deleted = true;
    }
}
