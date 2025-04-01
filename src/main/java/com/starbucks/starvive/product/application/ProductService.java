package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.infrastructure.ProductCustomRepository;
import com.starbucks.starvive.product.vo.ProductListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductCustomRepository productCustomRepository;

    public List<ProductListVO> getAllProducts() {
        return productCustomRepository.findAllProducts();
    }
}
