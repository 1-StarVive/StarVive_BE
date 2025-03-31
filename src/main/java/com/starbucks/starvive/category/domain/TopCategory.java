package com.starbucks.starvive.category.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID topCategoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbImageUrl;

    @Column(nullable = false)
    private String thumbAlt;

    @Builder
    public TopCategory(UUID topCategoryId, String name,
                       String thumbImageUrl, String thumbAlt) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }
}
