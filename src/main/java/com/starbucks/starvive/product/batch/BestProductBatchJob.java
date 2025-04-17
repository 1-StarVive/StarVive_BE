package com.starbucks.starvive.product.batch;

import com.starbucks.starvive.product.domain.BestProduct;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.infrastructure.BestProductRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class BestProductBatchJob {

    private final ProductRepository productRepository;
    private final BestProductRepository bestProductRepository;
    private final StringRedisTemplate redisTemplate;

    private static final int TOP_N = 10;
    private static final String LIKE_COUNT_KEY_PREFIX = "product:";
    private static final String LIKE_COUNT_KEY_SUFFIX = ":liked_by";

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void createBestProducts() {
        log.info("베스트 상품 배치 작업을 시작합니다...");

        List<UUID> allProductIds = productRepository.findAll().stream()
                .map(Product::getProductId)
                .toList();

        log.debug("전체 상품 개수: {}", allProductIds.size());

        Map<UUID, Long> productLikeCounts = new HashMap<>();
        for (UUID productId : allProductIds) {
            String redisKey = LIKE_COUNT_KEY_PREFIX + productId + LIKE_COUNT_KEY_SUFFIX;
            Long likeCount = redisTemplate.opsForSet().size(redisKey);
            productLikeCounts.put(productId, (likeCount != null) ? likeCount : 0L);
        }

        List<UUID> bestProductIds = productLikeCounts.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(TOP_N)
                .map(Map.Entry::getKey)
                .toList();

        log.info("상위 {}개 베스트 상품 선정 완료.", bestProductIds.size());

        bestProductRepository.deleteAllInBatch();
        log.debug("기존 베스트 상품 데이터 삭제 완료.");

        int rank = 1;
        for (UUID productId : bestProductIds) {
            BestProduct bestProduct = BestProduct.builder()
                    .productId(productId)
                    .bestRank(rank++)
                    .build();
            bestProductRepository.save(bestProduct);
        }
        log.info("새로운 베스트 상품 데이터 {}개 저장 완료.", bestProductIds.size());

        log.info("베스트 상품 배치 작업을 완료했습니다.");
    }
} 