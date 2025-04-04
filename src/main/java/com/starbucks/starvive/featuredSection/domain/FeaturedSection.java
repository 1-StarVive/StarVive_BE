package com.starbucks.starvive.featuredSection.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class FeaturedSection {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID featuredSectionId; // 추천 섹션 ID (PK)

    private String name; // 추천 섹션 이름
    private boolean activated;

    public FeaturedSection(UUID featuredSectionId, String name, boolean activated) {
        this.featuredSectionId = featuredSectionId;
        this.name = name;
        this.activated = activated;
    }

}
