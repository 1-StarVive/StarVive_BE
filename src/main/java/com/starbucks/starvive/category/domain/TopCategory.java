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
public class TopCategory extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "top_category_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID topCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbImageUrl;

    @Column(nullable = false)
    private String thumbAlt;

    @Builder
    public TopCategory(String name, String thumbImageUrl, String thumbAlt) {
        this.topCategoryId = UUID.randomUUID();
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }
}
