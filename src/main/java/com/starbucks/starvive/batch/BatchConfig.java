package com.starbucks.starvive.batch;

import com.starbucks.starvive.batch.dto.ProductWishCountDto;
import com.starbucks.starvive.product.batch.UpdateBestProductTasklet;
import com.starbucks.starvive.product.domain.Product;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import com.starbucks.starvive.batch.listener.TruncateTempTableListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final UpdateBestProductTasklet updateBestProductTasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;
    private final StringRedisTemplate redisTemplate;
    private final TruncateTempTableListener truncateTempTableListener;

    private static final String PRODUCT_LIKED_BY_KEY_PREFIX = "product:";
    private static final String PRODUCT_LIKED_BY_KEY_SUFFIX = ":liked_by";

    @Value("${chunkSize:1000}")
    private int chunkSize;

    @Bean
    public Job bestProductJob() {

        log.info(">>>>> bestProductJob 생성");
        return new JobBuilder("bestProductJob", jobRepository)
                .listener(truncateTempTableListener)
                .start(calculateWishCountStep())
                .next(updateBestProductStep())
                .build();
    }

    @Bean
    public Step calculateWishCountStep() {

        log.info(">>>>> calculateWishCountStep 생성 (Chunk Size: {})", chunkSize);
        return new StepBuilder("calculateWishCountStep", jobRepository)
                .<Product, ProductWishCountDto>chunk(chunkSize, transactionManager)
                .reader(productItemReader())
                .processor(productWishCountProcessor())
                .writer(tempProductWishCountWriter())
                .build();
    }

    @Bean
    public ItemReader<Product> productItemReader() {

        log.info(">>>>> productItemReader 생성");
        return new JpaPagingItemReaderBuilder<Product>()
                .name("productItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Product p ORDER BY p.id ASC")
                .build();
    }

    @Bean
    public ItemProcessor<Product, ProductWishCountDto> productWishCountProcessor() {

        log.info(">>>>> productWishCountProcessor 생성 (Redis 조회 방식)");
        return product -> {
            String productLikedByKey = PRODUCT_LIKED_BY_KEY_PREFIX + product.getProductId() + PRODUCT_LIKED_BY_KEY_SUFFIX;
            Long wishCountLong = redisTemplate.opsForSet().size(productLikedByKey);
            long wishCount = Objects.requireNonNullElse(wishCountLong, 0L);

            log.trace("Processing Product ID: {}, Wish Count from Redis: {}", product.getProductId(), wishCount);
            return new ProductWishCountDto(product.getProductId(), wishCount);
        };
    }

    @Bean
    public ItemWriter<ProductWishCountDto> tempProductWishCountWriter() {

        log.info(">>>>> tempProductWishCountWriter 생성");
        String sql = "INSERT INTO temp_product_wish_count (product_id, wish_count) " +
                     "VALUES (UNHEX(REPLACE(:productId, '-', '')), :wishCount) " +
                     "ON DUPLICATE KEY UPDATE wish_count = :wishCount";

        return new JdbcBatchItemWriterBuilder<ProductWishCountDto>()
                .dataSource(dataSource)
                .sql(sql)
                .itemSqlParameterSourceProvider(item -> {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("productId", item.productId().toString());
                    parameterSource.addValue("wishCount", item.wishCount());
                    return parameterSource;
                })
                .build();
    }

    @Bean
    public Step updateBestProductStep() {
        
        log.info(">>>>> updateBestProductStep 생성");
        return new StepBuilder("updateBestProductStep", jobRepository)
                .tasklet(updateBestProductTasklet, transactionManager)
                .build();
    }
} 