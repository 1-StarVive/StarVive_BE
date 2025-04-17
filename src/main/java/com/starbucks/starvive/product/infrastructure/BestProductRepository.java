package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.BestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BestProductRepository extends JpaRepository<BestProduct, UUID> {
    
    void deleteAllInBatch();
} 