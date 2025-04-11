package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.dto.in.DeleteTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.in.UpdateTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.TopCategoryResponseDto;
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

    @Override
    @Transactional
    public void addTopCategory(TopCategoryRequest topCategoryRequest) {
        if (topCategoryRepository.findByNameAndDeletedFalse(topCategoryRequest.getName()).isPresent()) {
            throw new BaseException(DUPLICATED_OPTION);
        }
        topCategoryRepository.save(topCategoryRequest.toEntity());
    }

    @Override
    public List<TopCategoryResponseDto> findTopCategories() {
        return topCategoryRepository.findAllByDeletedFalse()
                .stream().map(TopCategoryResponseDto:: from).toList();
    }

    @Override
    public TopCategoryResponseDto findTopCategoriesId(UUID topCategoryId) {
        TopCategory topCategory = topCategoryRepository.findByTopCategoryIdAndDeletedFalse(topCategoryId)
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));
        return TopCategoryResponseDto.from(topCategory);
    }

    @Transactional
    @Override
    // TopCategoryRequestDto -> UpdateTopCategoryRequestDto 클래스명 쪼개서 나누기
    public void updateTopCategory(UpdateTopCategoryRequestDto updateTopCategoryRequestDto) {
        TopCategory topCategory = topCategoryRepository.findById(updateTopCategoryRequestDto.getTopCategoryId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        topCategory.updateName(updateTopCategoryRequestDto);
    }

    @Transactional
    @Override
    // TopCategoryRequestDto -> DeleteTopCategoryRequestDto 클래스명 쪼개서 나누기
    public void deleteTopCategory(DeleteTopCategoryRequestDto deleteTopCategoryRequestDto) {
        TopCategory topCategory = topCategoryRepository.findById(deleteTopCategoryRequestDto.getTopCategoryId())
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        topCategory.softDelete();
    }
}
