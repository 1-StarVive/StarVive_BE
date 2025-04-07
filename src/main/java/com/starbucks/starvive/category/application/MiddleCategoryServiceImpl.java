package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequest;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponse;
import com.starbucks.starvive.category.infrastructure.MiddleCategoryRepository;
import com.starbucks.starvive.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MiddleCategoryServiceImpl implements MiddleCategoryService {

    private final MiddleCategoryRepository middleCategoryRepository;

    @Transactional
    @Override
    public void addMiddleCategory(MiddleCategoryRequest middleCategoryRequest) {
        if(middleCategoryRepository
                .findByNameAndTopCategoryId(middleCategoryRequest.getName(), middleCategoryRequest.getTopCategoryId())
                .isPresent()) {
            throw new BaseException(DUPLICATED_CATEGORY);
        }

        MiddleCategory middleCategory = middleCategoryRequest.toEntity();
        middleCategoryRepository.save(middleCategory);
    }

    @Override
    public List<MiddleCategoryResponse> findMiddleCategories(UUID topCategoryId) {
        return middleCategoryRepository.findAllByTopCategoryIdAndDeletedFalse(topCategoryId)
                .stream().map(MiddleCategoryResponse::from).toList();
    }

    @Override
    public List<MiddleCategoryResponse> findMiddleCategories() {
        return middleCategoryRepository.findAllByDeletedFalse()
                .stream().map(MiddleCategoryResponse::from).toList();
    }

    @Transactional
    @Override
    public void updateMiddleCategory(UUID middleCategoryId, MiddleCategoryRequest middleCategoryRequest) {
        MiddleCategory middleCategory = middleCategoryRepository.findById(middleCategoryId)
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        middleCategory.updateMiddleCategory(middleCategoryRequest);
    }

    @Transactional
    @Override
    public void deleteMiddleCategory(UUID middleCategoryId) {
        MiddleCategory middleCategory = middleCategoryRepository.findById(middleCategoryId)
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        middleCategory.softDelete();
    }
}
