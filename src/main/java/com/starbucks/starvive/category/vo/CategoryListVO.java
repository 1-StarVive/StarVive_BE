package com.starbucks.starvive.category.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVO {

    private UUID categoryId;

    private String categoryName;

    private String categoryUrl;

    private String categoryAlt;
}
