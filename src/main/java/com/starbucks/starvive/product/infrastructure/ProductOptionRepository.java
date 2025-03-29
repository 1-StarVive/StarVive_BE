package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOptionRepository extends JpaRepository<ProductOption, UUID> {
}
