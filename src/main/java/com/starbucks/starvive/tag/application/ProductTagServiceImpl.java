package com.starbucks.starvive.tag.application;

import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.tag.domain.ProductTag;
import com.starbucks.starvive.tag.dto.in.RegisterProductTagRequestDto;
import com.starbucks.starvive.tag.dto.out.ProductTagListResponseDto;
import com.starbucks.starvive.tag.infrastructure.ProductTagCustomRepository;
import com.starbucks.starvive.tag.infrastructure.ProductTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.DUPLICATED_PRODUCT_TAG;

@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;

    private final ProductTagCustomRepository productTagCustomRepository;

    @Transactional
    @Override
    public void addProductTag(RegisterProductTagRequestDto registerProductTagRequestDto) {

        for (UUID productId : registerProductTagRequestDto.getProductIds()) {
            if (productTagRepository.existsByProductIdAndTagId(productId, registerProductTagRequestDto.getTagId())) {
                throw new BaseException(DUPLICATED_PRODUCT_TAG);
            }

            // 새 ProductTag 생성 및 저장
            ProductTag productTag = ProductTag.builder()
                    .productId(productId)
                    .tagId(registerProductTagRequestDto.getTagId())
                    .build();

            productTagRepository.save(productTag);
        }
    }

    @Override
    public List<ProductTagListResponseDto> getProductTagList(UUID tagId) {
        return productTagCustomRepository.findAllTagProducts(tagId);
    }
}
