package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.DeleteMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface MiddleCategoryService {

    void addMiddleCategory(MiddleCategoryRequestDto middleCategoryRequest);

    MiddleCategoryResponseDto findMiddleCategoryById(UUID middleCategoryId);

    List<MiddleCategoryResponseDto> findMiddleCategoryByTopId(UUID topCategoryId);

    List<MiddleCategoryResponseDto> findMiddleCategories();

    void updateMiddleCategory(UpdateMiddleCategoryRequestDto updateMiddleCategoryRequestDto);

    void deleteMiddleCategory(DeleteMiddleCategoryRequestDto deleteMiddleCategoryRequestDto);
}
