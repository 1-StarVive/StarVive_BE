package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.vo.ProductListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCustomRepository {
    List<ProductListVO> findAllProducts();
}
