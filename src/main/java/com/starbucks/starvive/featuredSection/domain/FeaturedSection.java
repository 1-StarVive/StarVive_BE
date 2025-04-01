package com.starbucks.starvive.featuredSection.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Getter
@Entity
public class FeaturedSection {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name; // 추천 섹션 이름

    @Column(nullable = false)
    private boolean activated;


    //@Column(columnDefinition = "BINARY(16)", nullable = false)
    //private String productId;


}
