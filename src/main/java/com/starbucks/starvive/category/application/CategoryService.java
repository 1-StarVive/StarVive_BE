package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.CategoryListResponseDTO;
import com.starbucks.starvive.category.infrastructure.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

//    public List<CategoryListResponseDTO> getCategories() {
//        return categoryRepository.findAllCategories().stream()
//                .map(CategoryListResponseDTO::fromCategoryVo)
//                .toList();
//    }
}
