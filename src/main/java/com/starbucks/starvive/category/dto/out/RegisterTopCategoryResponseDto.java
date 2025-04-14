package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.domain.TopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterTopCategoryResponseDto {

    private UUID topCategoryId;

    private String name;

    private String imageUrl;

    private String imageAlt;

    @Builder
    public RegisterTopCategoryResponseDto(UUID topCategoryId, String name,
                                          String imageUrl, String imageAlt) {
        this.topCategoryId = topCategoryId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
    }

    public static RegisterTopCategoryResponseDto from(TopCategory topCategory) {
        return RegisterTopCategoryResponseDto.builder()
                .topCategoryId(topCategory.getTopCategoryId())
                .name(topCategory.getName())
                .imageUrl(topCategory.getImageUrl())
                .imageAlt(topCategory.getImageAlt())
                .build();
    }
}
