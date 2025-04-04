package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, UUID> {

    Optional<ProductOption> findByProductOptionId(UUID productOptionId);

}
