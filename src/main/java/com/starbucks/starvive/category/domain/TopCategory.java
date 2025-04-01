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
public class TopCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbImageUrl;

    @Column(nullable = false)
    private String thumbAlt;

    @Builder
    public TopCategory(String name,
                       String thumbImageUrl, String thumbAlt) {
        this.name = name;
        this.thumbImageUrl = thumbImageUrl;
        this.thumbAlt = thumbAlt;
    }
}
