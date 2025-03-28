package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.out.ProductListResponseDTO;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    public List<ProductListResponseDTO> getAllProducts() {
//
//        return productRepository.findAllProducts().stream()
//                .map(ProductListResponseDTO::fromProductVo)
//                .toList();
//    }
}
