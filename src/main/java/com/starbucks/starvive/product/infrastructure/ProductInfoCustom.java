package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductInfoCustom {

    List<ProductRequiredInfoResponseDto> findRequiredInfosByProductId(UUID productId);
}
