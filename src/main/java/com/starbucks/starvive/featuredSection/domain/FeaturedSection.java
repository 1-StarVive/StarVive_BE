package com.starbucks.starvive.featuredSection.domain;

import jakarta.persistence.*;
import lombok.Builder;
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
    private UUID featuredSectionId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean activated;

    @Builder
    public FeaturedSection(UUID featuredSectionId, String name, boolean activated) {
        this.featuredSectionId = featuredSectionId;
        this.name = name;
        this.activated = activated;
    }

    public void update(String name, boolean activated) {
        this.name = name;
        this.activated = activated;
    }
}
