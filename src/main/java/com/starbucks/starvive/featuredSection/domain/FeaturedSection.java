package com.starbucks.starvive.featuredSection.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeaturedSection {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID featuredSectionId; // 추천 섹션 ID (PK)
    private String featuredSectionName; // 추천 섹션 이름

    //private String bannerImageUrl;

    @OneToMany(mappedBy = "featuredSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeaturedSectionProduct> products = new ArrayList<>(); // 해당 섹션에 포함된 상품들


}
