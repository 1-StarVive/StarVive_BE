package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

  Optional<Product> findByProductId(UUID productId);

  List<Product> findAllByOrderByProductIdDesc(Pageable pageable);

  List<Product> findByProductIdLessThanOrderByProductIdDesc(UUID lastProductId, Pageable pageable);

}
