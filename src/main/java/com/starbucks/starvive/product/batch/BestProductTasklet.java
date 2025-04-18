package com.starbucks.starvive.product.batch;

import com.starbucks.starvive.product.domain.BestProduct;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.infrastructure.BestProductRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component; // Tasklet을 Bean으로 등록하기 위해
import org.springframework.transaction.annotation.Transactional; // 필요시 트랜잭션 관리

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component // Spring Batch 설정에서 Bean으로 참조할 수 있도록 Component로 등록
@RequiredArgsConstructor
public class BestProductTasklet implements Tasklet {

    private final ProductRepository productRepository;
    private final BestProductRepository bestProductRepository;
    private final StringRedisTemplate redisTemplate;

    private static final int TOP_N = 10;
    private static final String LIKE_COUNT_KEY_PREFIX = "product:";
    private static final String LIKE_COUNT_KEY_SUFFIX = ":liked_by";

    @Override
    @Transactional // 기존 로직처럼 전체를 한 트랜잭션으로 묶음 (선택 사항)
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>> BestProduct Tasklet 시작...");

        // 1. 모든 상품 ID 가져오기
        List<UUID> allProductIds = productRepository.findAll().stream()
                .map(Product::getProductId)
                .toList();
        log.debug("전체 상품 개수: {}", allProductIds.size());

        // 2. 상품별 좋아요 수 집계 (Redis 조회)
        Map<UUID, Long> productLikeCounts = new HashMap<>();
        for (UUID productId : allProductIds) {
            String redisKey = LIKE_COUNT_KEY_PREFIX + productId + LIKE_COUNT_KEY_SUFFIX;
            Long likeCount = redisTemplate.opsForSet().size(redisKey);
            productLikeCounts.put(productId, (likeCount != null) ? likeCount : 0L);
        }

        // 3. 좋아요 수 기준으로 상위 N개 상품 ID 선정
        List<UUID> bestProductIds = productLikeCounts.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(TOP_N)
                .map(Map.Entry::getKey)
                .toList();
        log.info("상위 {}개 베스트 상품 선정 완료.", bestProductIds.size());

        // 4. 기존 BestProduct 데이터 삭제
        bestProductRepository.deleteAllInBatch(); // deleteAll() 또는 deleteAllInBatch() 사용
        log.debug("기존 베스트 상품 데이터 삭제 완료.");

        // 5. 새로운 BestProduct 데이터 저장
        int currentRank = 1;
        List<BestProduct> newBestProducts = new ArrayList<>();
        for (UUID productId : bestProductIds) {
            BestProduct bestProduct = BestProduct.builder()
                    .productId(productId)
                    .bestRank(currentRank++)
                    .build();
            newBestProducts.add(bestProduct);
        }
        bestProductRepository.saveAll(newBestProducts); // saveAll() 로 한 번에 저장
        log.info("새로운 베스트 상품 데이터 {}개 저장 완료.", bestProductIds.size());

        log.info("<<<< BestProduct Tasklet 완료.");
        return RepeatStatus.FINISHED; // Tasklet 작업 완료 상태 반환
    }
} 