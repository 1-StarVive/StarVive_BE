package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WishRepository extends JpaRepository<Wish, UUID> {
}
