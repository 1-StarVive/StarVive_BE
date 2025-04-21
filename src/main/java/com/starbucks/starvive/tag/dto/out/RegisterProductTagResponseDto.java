package com.starbucks.starvive.tag.dto.out;

import com.starbucks.starvive.tag.domain.ProductTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterProductTagResponseDto {

    private UUID productId;
    private UUID tagId;

    @Builder
    public RegisterProductTagResponseDto(UUID productId, UUID tagId) {
        this.productId = productId;
        this.tagId = tagId;

    }

    public static RegisterProductTagResponseDto from(ProductTag productTag) {
        return RegisterProductTagResponseDto.builder()
                .productId(productTag.getProductId())
                .tagId(productTag.getTagId())
                .build();
    }
}
