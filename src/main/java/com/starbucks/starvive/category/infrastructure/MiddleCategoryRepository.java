package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.domain.MiddleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MiddleCategoryRepository extends JpaRepository<MiddleCategory, UUID> {

    Optional<MiddleCategory> findByNameAndTopCategoryId(String name, UUID topCategoryId);

    Optional<MiddleCategory> findByMiddleCategoryId(UUID middleCategoryId);

    List<MiddleCategory> findAllByDeletedFalse();

    // List<MiddleCategory> findAllByTopCategoryIdAndDeletedFalse(UUID topCategoryId);
}
