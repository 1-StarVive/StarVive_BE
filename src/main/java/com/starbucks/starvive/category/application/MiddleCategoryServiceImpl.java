package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;
import com.starbucks.starvive.category.infrastructure.MiddleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MiddleCategoryServiceImpl implements MiddleCategoryService {

    private final MiddleCategoryRepository middleCategoryRepository;

    @Override
    public List<MiddleCategoryResponse> findMiddleCategories(UUID topCategoryId) {
        return middleCategoryRepository.findByTopCategoryId(topCategoryId)
                .stream().map(MiddleCategoryResponse::from).toList();
    }
}
