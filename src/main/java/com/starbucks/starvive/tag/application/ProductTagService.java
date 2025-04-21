package com.starbucks.starvive.tag.application;

import com.starbucks.starvive.tag.dto.in.RegisterProductTagRequestDto;
import com.starbucks.starvive.tag.dto.out.ProductTagListResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductTagService {

    void addProductTag(RegisterProductTagRequestDto registerProductTagRequestDto);

    List<ProductTagListResponseDto> getProductTagList(UUID tagId);
}
