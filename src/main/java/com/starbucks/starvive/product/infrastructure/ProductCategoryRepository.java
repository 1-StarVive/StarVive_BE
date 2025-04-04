package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
