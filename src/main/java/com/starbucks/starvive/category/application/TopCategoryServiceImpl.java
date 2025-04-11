package com.starbucks.starvive.category.application;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.dto.in.TopCategoryRequest;
import com.starbucks.starvive.category.dto.out.TopCategoryResponse;
import com.starbucks.starvive.category.infrastructure.TopCategoryRepository;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.common.s3.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public List<TopCategoryResponse> findTopCategories() {
        return topCategoryRepository.findAllByDeletedFalse()
                .stream().map(TopCategoryResponse :: from).toList();
    }

    @Override
    public TopCategoryResponse findTopCategoriesId(UUID topCategoryId) {
        TopCategory topCategory = topCategoryRepository.findByTopCategoryIdAndDeletedFalse(topCategoryId)
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));
        return TopCategoryResponse.from(topCategory);
    }


    @Transactional
    @Override
    public void updateTopCategory(TopCategoryRequest topCategoryRequest) {
        TopCategory topCategory = topCategoryRepository.findById(topCategoryRequest.getTopCategoryId())
                .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));

        topCategory.updateName(topCategoryRequest);
    }

    @Transactional
    @Override
    public void deleteTopCategory(UUID topCategoryId) {
        TopCategory topCategory = topCategoryRepository.findById(topCategoryId)
                .orElseThrow(() -> new BaseException(ALREADY_DELETED_CATEGORY));

        topCategory.softDelete();
    }
}
