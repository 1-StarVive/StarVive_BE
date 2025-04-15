package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.category.dto.in.UpdateTopCategoryRequestDto;
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
public class TopCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID topCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("false")
    private String imageUrl;

    @ColumnDefault("false")
    private String imageAlt;

    @ColumnDefault("false")
    private boolean deleted = false;

    @Builder
    public TopCategory(UUID topCategoryId, String name,
                       String imageUrl, String imageAlt, boolean deleted) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
        this.deleted = deleted;
    }

    public void update(UpdateTopCategoryRequestDto updateTopCategoryRequestDto) {
        this.name = updateTopCategoryRequestDto.getName();
    }

    public void softDelete() {
        this.deleted = true;
    }
}
