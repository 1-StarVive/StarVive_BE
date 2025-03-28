package com.starbucks.starvive.category.dto.out;

import com.starbucks.starvive.category.vo.CategoryListVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponseDTO {

    private UUID categoryId;

    private String categoryName;

    private String url;

    private String alt;


    public static CategoryListResponseDTO fromCategoryVo(CategoryListVO categoryListVO) {
        return CategoryListResponseDTO.builder()
                .categoryId(categoryListVO.getCategoryId())
                .categoryName(categoryListVO.getCategoryName())
                .url(categoryListVO.getCategoryUrl())
                .alt(categoryListVO.getCategoryAlt())
                .build();
    }
}
