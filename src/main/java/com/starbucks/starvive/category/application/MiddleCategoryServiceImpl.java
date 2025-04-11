package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.MiddleCategory;
import com.starbucks.starvive.category.dto.in.DeleteMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.MiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateMiddleCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.MiddleCategoryResponseDto;
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
    public void addMiddleCategory(MiddleCategoryRequestDto middleCategoryRequest) {
        if(middleCategoryRepository
                .findByNameAndTopCategoryId(middleCategoryRequest.getName(), middleCategoryRequest.getTopCategoryId())
                .isPresent()) {
            throw new BaseException(DUPLICATED_CATEGORY);
        }

        middleCategoryRepository.save(middleCategoryRequest.toEntity());
    }

    @Override
    public MiddleCategoryResponseDto findMiddleCategoryById(UUID middleCategoryId) {
        MiddleCategory middleCategory = middleCategoryRepository.findByDeletedFalseAndMiddleCategoryId(middleCategoryId)
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));
        return MiddleCategoryResponseDto.from(middleCategory);
    }

    @Override
    public List<MiddleCategoryResponseDto> findMiddleCategories() {
        return middleCategoryRepository.findAllByDeletedFalse()
                .stream().map(MiddleCategoryResponseDto::from).toList();
    }

    @Transactional
    @Override
    public void updateMiddleCategory(UpdateMiddleCategoryRequestDto updateMiddleCategoryRequestDto) {
        MiddleCategory middleCategory = middleCategoryRepository.findById(updateMiddleCategoryRequestDto.getMiddleCategoryId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        middleCategory.update(updateMiddleCategoryRequestDto);
    }

    @Transactional
    @Override
    public void deleteMiddleCategory(DeleteMiddleCategoryRequestDto deleteMiddleCategoryRequestDto) {
        MiddleCategory middleCategory = middleCategoryRepository.findById(deleteMiddleCategoryRequestDto.getMiddleCategoryId())
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        middleCategory.softDelete();
    }
}
