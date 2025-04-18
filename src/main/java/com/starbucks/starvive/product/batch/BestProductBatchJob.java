package com.starbucks.starvive.product.batch;

import com.starbucks.starvive.product.domain.BestProduct;
import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.infrastructure.BestProductRepository;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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

    // @Transactional
    // @Scheduled(cron = "0 0 0 * * ?")
    // public void createBestProducts() { ... }

    // Keep the class for potential future use or just delete it later
} 