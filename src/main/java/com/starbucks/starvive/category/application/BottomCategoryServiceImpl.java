package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.infrastructure.BottomCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BottomCategoryServiceImpl implements BottomCategoryService {

    private final BottomCategoryRepository bottomCategoryRepository;

    @Override
    public List<BottomCategoryResponse> findBottomCategories(UUID middleCategoryId) {
        return bottomCategoryRepository.findByMiddleCategoryId(middleCategoryId)
                .stream().map(BottomCategoryResponse::from).toList();
    }
}
