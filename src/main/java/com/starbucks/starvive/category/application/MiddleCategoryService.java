package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.DeleteMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface MiddleCategoryService {

    void addMiddleCategory(MiddleCategoryRequestDto middleCategoryRequest);

    MiddleCategoryResponse findMiddleCategoryById(UUID middleCategoryId);

    List<MiddleCategoryResponse> findMiddleCategories();

    void updateMiddleCategory(UpdateMiddleCategoryRequestDto updateMiddleCategoryRequestDto);

    void deleteMiddleCategory(DeleteMiddleCategoryRequestDto deleteMiddleCategoryRequestDto);
}
