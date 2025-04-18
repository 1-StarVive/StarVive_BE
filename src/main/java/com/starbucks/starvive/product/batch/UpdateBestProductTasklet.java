package com.starbucks.starvive.product.batch;

import com.starbucks.starvive.product.domain.BestProduct;
import com.starbucks.starvive.product.infrastructure.BestProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Add transactional for DB operations

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateBestProductTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;
    private final BestProductRepository bestProductRepository;
    private static final int TOP_N = 30; 

    @Override
    @Transactional 
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>>> UpdateBestProduct Tasklet 시작...");

        // 1. 임시 테이블에서 상위 N개 상품 ID 조회
        log.info("임시 테이블에서 상위 {}개 상품 조회 시작...", TOP_N);
        // BINARY(16) -> UUID 변환 필요. HEX() 사용
        String selectTopSql = "SELECT HEX(product_id) as product_id_hex " +
                              "FROM temp_product_wish_count " +
                              "ORDER BY wish_count DESC " +
                              "LIMIT ?";

        List<UUID> topProductIds = jdbcTemplate.query(selectTopSql, ps -> ps.setInt(1, TOP_N), (rs, rowNum) -> {
            String hex = rs.getString("product_id_hex");
            // UUID 포맷 (8-4-4-4-12) 에 맞게 하이픈 삽입
            String formattedHex = String.format("%s-%s-%s-%s-%s",
                    hex.substring(0, 8),
                    hex.substring(8, 12),
                    hex.substring(12, 16),
                    hex.substring(16, 20),
                    hex.substring(20, 32));
            return UUID.fromString(formattedHex);
        });
        log.info("상위 {}개 상품 ID 조회 완료 ({}개 발견)", TOP_N, topProductIds.size());

        // 2. 기존 best_product 테이블 데이터 삭제
        log.info("기존 BestProduct 데이터 삭제 시작...");
        bestProductRepository.deleteAllInBatch(); // JPA Batch Delete 사용
        log.info("기존 BestProduct 데이터 삭제 완료.");

        // 3. 새로운 BestProduct 데이터 저장
        log.info("새로운 BestProduct 데이터 저장 시작...");
        List<BestProduct> newBestProducts = new ArrayList<>();
        for (int i = 0; i < topProductIds.size(); i++) {
            UUID productId = topProductIds.get(i);
            int rank = i + 1;
            BestProduct bestProduct = BestProduct.builder()
                    .productId(productId)
                    .bestRank(rank)
                    .build();
            newBestProducts.add(bestProduct);
            log.trace("Rank {}: Product ID {}", rank, productId);
        }

        bestProductRepository.saveAll(newBestProducts); // JPA Batch Insert 사용
        log.info("새로운 BestProduct 데이터 {}개 저장 완료.", newBestProducts.size());

        // 4. (선택사항) 임시 테이블 비우기 (다음 실행을 위해)
        // log.info("임시 테이블 데이터 삭제 시작...");
        // jdbcTemplate.update("TRUNCATE TABLE temp_product_wish_count");
        // log.info("임시 테이블 데이터 삭제 완료.");
        // Job Listener에서 처리하는 것이 더 일반적일 수 있음

        log.info("<<<<< UpdateBestProduct Tasklet 완료.");
        return RepeatStatus.FINISHED;
    }
} 