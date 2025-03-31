package com.starbucks.starvive.featuredSection.dto.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


@Getter
@NoArgsConstructor(force = true) // final 필드 초기화 위해 필요
public class FeaturedSectionProductRequestDto {

    private final List<String> featuredSectionsIds;
    private String name;
    // 👇 명시적 생성자 (Jackson이 자동 매핑)

    //public FeaturedSectionProductRequestDto(List<String> featuredSectionsIds) {
        //this.featuredSectionsIds = featuredSectionsIds;
    //}
}