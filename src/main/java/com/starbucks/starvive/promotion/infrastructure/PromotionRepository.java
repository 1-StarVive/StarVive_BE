package com.starbucks.starvive.promotion.infrastructure;

import com.starbucks.starvive.promotion.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {

    Optional<Promotion> findByTitle(String title);
}
