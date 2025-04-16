package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.dto.out.ProductListResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoryCustomRepository {

    List<ProductListResponseDto> findProductsByCategory(UUID topCategoryId,
                                                        UUID middleCategoryId,
                                                        UUID bottomCategoryId);

}
