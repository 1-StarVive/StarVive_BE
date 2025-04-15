package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.in.DeleteTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.TopCategoryResponseDto;
import com.starbucks.starvive.category.vo.RegisterTopCategoryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TopCategoryService {

    void addTopCategory(RegisterTopCategoryVo registerTopCategoryRequestVo, MultipartFile multipartFile);

    TopCategoryResponseDto findTopCategoriesId(UUID topCategoryId);

    List<TopCategoryResponseDto> findTopCategories();

    void updateTopCategory(UpdateTopCategoryRequestDto updateTopCategoryRequestDto);

    void deleteTopCategory(DeleteTopCategoryRequestDto deleteTopCategoryRequestDto);
}
