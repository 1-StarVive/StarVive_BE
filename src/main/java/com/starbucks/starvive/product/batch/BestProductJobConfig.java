package com.starbucks.starvive.product.batch;

import com.starbucks.starvive.product.application.WishRankingService;
import com.starbucks.starvive.product.dto.WishCountDto;
import com.starbucks.starvive.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class BestProductJobConfig {

//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final ProductRepository productRepository;
//    private final WishRankingService wishRankingService;
//
//    @Bean
//    public Job bestProductJob() {
//        return new JobBuilder("bestProductJob", jobRepository)
//                .start(bestProductStep())
//                .build();
//    }
//
//    @Bean
//    public Step bestProductStep() {
//        List<WishCountDto> topWishes = wishRankingService.getTopWishes(100);
//        ItemReader<WishCountDto> reader = new IteratorItemReader<>(topWishes);
//
//        return new StepBuilder("bestProductStep", jobRepository)
//                .<WishCountDto, UUID>chunk(100, transactionManager)
//                .reader(reader)
//                .processor(new BestProductProcessor())
//                .writer(new BestProductWriter(productRepository))
//                .build();
//    }
}
