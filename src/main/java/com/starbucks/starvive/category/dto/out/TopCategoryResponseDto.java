package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.TopCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
// TopCategoryResponse -> TopCategoryResponseDto 이렇게 Dto 붙여주세요 !
public class TopCategoryResponseDto {

    private UUID topCategoryId;

    private String name;

    private String imageUrl;

    private String imageAlt;

    // @Builder -> 생성자 선언 후 @AllArgsConstructor 빼기
    @Builder
    public TopCategoryResponseDto(UUID topCategoryId, String name,
                                  String imageUrl, String imageAlt) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
    }

    public static TopCategoryResponseDto from(TopCategory topCategory) {
        return TopCategoryResponseDto.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .imageUrl(topCategory.getImageUrl())
                .imageAlt(topCategory.getImageAlt())
                .build();
    }
}
