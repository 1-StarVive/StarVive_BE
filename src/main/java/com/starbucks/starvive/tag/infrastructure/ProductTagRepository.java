package com.starbucks.starvive.tag.infrastructure;

import com.starbucks.starvive.tag.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, UUID> {

    boolean existsByProductIdAndTagId(UUID productId, UUID tagId);
}
