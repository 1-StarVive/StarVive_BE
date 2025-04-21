package com.starbucks.starvive.tag.infrastructure;

import com.starbucks.starvive.tag.dto.out.ProductTagListResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductTagCustomRepository {

    List<ProductTagListResponseDto> findAllTagProducts(UUID tagId);
}
