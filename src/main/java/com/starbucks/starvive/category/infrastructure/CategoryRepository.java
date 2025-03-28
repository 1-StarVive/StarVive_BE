package com.starbucks.starvive.category.infrastructure;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.vo.CategoryListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<TopCategory, UUID> {

    @Query("""
    SELECT new com.starbucks.starvive.category.vo.CategoryListVO(
        c.categoryId, c.categoryName, c.url, c.alt)
    FROM TopCategory c
    """)
    List<CategoryListVO> findAllCategories();

    // List<Category> findAll();
}
