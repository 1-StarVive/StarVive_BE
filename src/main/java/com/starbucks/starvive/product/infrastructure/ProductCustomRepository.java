package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.dto.out.ProductRequiredInfoResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCustomRepository {

    ProductDetailResponseDto findProductDetailById(UUID productId);


}
