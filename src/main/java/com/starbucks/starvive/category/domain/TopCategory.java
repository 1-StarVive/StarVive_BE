package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
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
public class TopCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID topCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbImageUrl;

    @Column(nullable = false)
    private String thumbAlt;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public TopCategory(UUID topCategoryId, String name, String thumbImageUrl, String thumbAlt) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }

    public void update(TopCategoryRequest topCategoryRequest) {
        this.name = topCategoryRequest.getName();
        this.thumbImageUrl = topCategoryRequest.getThumbImageUrl();
        this.thumbAlt = topCategoryRequest.getThumbAlt();
    }

    public void softDelete() {
        this.deleted = true;
    }
}
