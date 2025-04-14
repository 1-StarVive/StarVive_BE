package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.DeleteTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.RegisterTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.TopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.TopCategoryResponseDto;
import com.starbucks.starvive.category.vo.RegisterTopCategoryRequestVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TopCategoryService {

    void addTopCategory(RegisterTopCategoryRequestVo registerTopCategoryRequestVo, MultipartFile multipartFile);

    TopCategoryResponseDto findTopCategoriesId(UUID topCategoryId);

    List<TopCategoryResponseDto> findTopCategories();

    void updateTopCategory(UpdateTopCategoryRequestDto updateTopCategoryRequestDto);

    void deleteTopCategory(DeleteTopCategoryRequestDto deleteTopCategoryRequestDto);
}
