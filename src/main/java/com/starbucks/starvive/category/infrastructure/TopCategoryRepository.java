package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.domain.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopCategoryRepository extends JpaRepository<TopCategory, UUID> {

    Optional<TopCategory> findByNameAndDeletedFalse(String name);

    List<TopCategory> findAllByDeletedFalse();
}
