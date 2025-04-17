package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.BestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BestProductRepository extends JpaRepository<BestProduct, UUID> {
    // 기본 CRUD 메서드 (save, findById, findAll, delete 등)는 JpaRepository가 제공합니다.
    // 필요에 따라 추가적인 쿼리 메서드를 정의할 수 있습니다.
    // 예를 들어, 모든 베스트 상품을 순위 순으로 조회하는 메서드:
    // List<BestProduct> findAllByOrderByRankAsc();

    // 배치 작업 시 기존 데이터를 모두 지우기 위해 이 메서드를 사용할 수 있습니다.
    void deleteAllInBatch(); // deleteAll() 보다 효율적일 수 있음
} 