package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryListResponse;
import com.starbucks.starvive.category.infrastructure.TopCategoryRepository;
import com.starbucks.starvive.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class TopCategoryServiceImpl implements TopCategoryService {

    private final TopCategoryRepository topCategoryRepository;

    @Transactional
    @Override
    public void addTopCategory(TopCategoryRequest topCategoryRequest) {
        if (topCategoryRepository.findByName(topCategoryRequest.getName()).isPresent()) {
            throw new BaseException(DUPLICATED_OPTION);
        }

        TopCategory topCategory = topCategoryRequest.toEntity();
        topCategoryRepository.save(topCategory);
    }

    @Override
    public List<TopCategoryListResponse> findTopCategories() {
        return topCategoryRepository.findAllByDeletedFalse()
                .stream().map(TopCategoryListResponse :: from).toList();
    }

    @Transactional
    @Override
    public void updateTopCategory(UUID topCategoryId, TopCategoryRequest topCategoryRequest) {
        TopCategory topCategory = topCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        topCategory.update(topCategoryRequest);
    }

    @Transactional
    @Override
    public void deleteTopCategory(UUID topCategoryId) {
        TopCategory topCategory = topCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        topCategory.softDelete();
    }
}
