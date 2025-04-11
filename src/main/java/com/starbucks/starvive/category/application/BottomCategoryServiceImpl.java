package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.BottomCategory;
import com.starbucks.starvive.category.dto.in.BottomCategoryRequest;
import com.starbucks.starvive.category.dto.in.DeleteBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.infrastructure.BottomCategoryRepository;
import com.starbucks.starvive.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BottomCategoryServiceImpl implements BottomCategoryService {

    private final BottomCategoryRepository bottomCategoryRepository;

    @Transactional
    @Override
    public void addBottomCategory(BottomCategoryRequest bottomCategoryRequest) {
        if(bottomCategoryRepository
                .findByNameAndMiddleCategoryId(bottomCategoryRequest.getName(), bottomCategoryRequest.getMiddleCategoryId())
                .isPresent()) {
            throw new BaseException(DUPLICATED_CATEGORY);
        }

        bottomCategoryRepository.save(bottomCategoryRequest.toCategory());
    }

    @Override
    public List<BottomCategoryResponse> findBottomCategories(UUID middleCategoryId) {
        return bottomCategoryRepository.findByMiddleCategoryIdAndDeletedFalse(middleCategoryId)
                .stream().map(BottomCategoryResponse::from).toList();
    }

    @Override
    public List<BottomCategoryResponse> findBottomCategories() {
        return bottomCategoryRepository.findAllByDeletedFalse()
                .stream().map(BottomCategoryResponse::from).toList();
    }

    @Transactional
    @Override
    public void updateBottomCategory(UpdateBottomCategoryRequestDto updateBottomCategoryRequestDto) {
        BottomCategory bottomCategory = bottomCategoryRepository.findById(updateBottomCategoryRequestDto.getBottomCategoryId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        bottomCategory.update(updateBottomCategoryRequestDto);
    }

    @Transactional
    @Override
    public void deleteBottomCategory(DeleteBottomCategoryRequestDto deleteBottomCategoryRequestDto) {
        BottomCategory bottomCategory = bottomCategoryRepository.findById(deleteBottomCategoryRequestDto.getBottomCategoryId())
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        bottomCategory.softDelete();
    }
}
