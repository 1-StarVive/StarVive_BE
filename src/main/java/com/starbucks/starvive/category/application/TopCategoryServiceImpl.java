package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.infrastructure.TopCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopCategoryServiceImpl implements TopCategoryService {

    private final TopCategoryRepository topCategoryRepository;

    // 상위 카테고리 조회
    @Override
    public List<TopCategoryResponse> findTopCategories() {
        return topCategoryRepository.findAll()
                .stream().map(TopCategoryResponse :: from).toList();
    }
}
