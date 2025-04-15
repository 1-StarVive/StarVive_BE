package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.BottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.DeleteBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponseDto;
import java.util.List;
import java.util.UUID;

public interface BottomCategoryService {

    void addBottomCategory(BottomCategoryRequestDto bottomCategoryRequest);

    List<BottomCategoryResponseDto> findBottomCategories(UUID middleCategoryId);

    List<BottomCategoryResponseDto> findBottomCategories();

    void updateBottomCategory(UpdateBottomCategoryRequestDto updateBottomCategoryRequestDto);

    void deleteBottomCategory(DeleteBottomCategoryRequestDto deleteBottomCategoryRequestDto);
}
