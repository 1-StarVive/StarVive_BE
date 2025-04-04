package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;
import com.starbucks.starvive.category.infrastructure.CategoryCustomRepository;
import com.starbucks.starvive.category.infrastructure.TopCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TopCategoryServiceImpl implements TopCategoryService {

    private final TopCategoryRepository topCategoryRepository;
    private final CategoryCustomRepository categoryCustomRepository;

    @Override
    public List<TopCategoryResponse> findTopCategories() {
        return topCategoryRepository.findAll()
                .stream().map(TopCategoryResponse :: from).toList();
    }

    @Override
    public List<TopWithMiddleBottomCategoryResponse> findCategoryByTopCategoryId(UUID topCategoryId) {
        return categoryCustomRepository.findCategoryByTopCategoryId(topCategoryId);
    }
}
