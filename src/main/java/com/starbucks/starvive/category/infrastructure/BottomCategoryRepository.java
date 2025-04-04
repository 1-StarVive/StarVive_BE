package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.domain.BottomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BottomCategoryRepository extends JpaRepository<BottomCategory, UUID> {

    /**
     *
     * @param middleCategoryId
     * @return
     */
    List<BottomCategory> findByMiddleCategoryId(UUID middleCategoryId);
}
