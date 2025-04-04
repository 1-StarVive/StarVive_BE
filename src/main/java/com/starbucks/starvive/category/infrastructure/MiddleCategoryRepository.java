package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.domain.MiddleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MiddleCategoryRepository extends JpaRepository<MiddleCategory, UUID> {

    List<MiddleCategory> findByTopCategoryId(UUID topCategoryId);
}
