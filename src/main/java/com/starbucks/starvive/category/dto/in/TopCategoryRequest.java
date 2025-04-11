package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.vo.TopCategoryRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class TopCategoryRequest {

    private UUID topCategoryId;

    private String name;

    @Builder
    public TopCategoryRequest(UUID topCategoryId, String name) {
        this.topCategoryId = topCategoryId;
        this.name = name;
    }

    public TopCategory toEntity() {
        return TopCategory.builder()
                .name(name)
                .build();
    }

    public static TopCategoryRequest from(TopCategoryRequestVo topCategoryVo) {
        return TopCategoryRequest.builder()
                .topCategoryId(topCategoryVo.getTopCategoryId())
                .name(topCategoryVo.getName())
                .build();
    }
}
