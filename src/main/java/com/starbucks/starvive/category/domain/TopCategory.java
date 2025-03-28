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
public class TopCategory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID topCategoryId;

    private String name;

    private String thumbImageUrl;

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
