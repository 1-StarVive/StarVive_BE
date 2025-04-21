package com.starbucks.starvive.batch; 

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher; 
    private final Job bestProductJob;      

    @Scheduled(cron = "0 0 0 * * ?")
    public void runBestProductJob() {
        try {
            log.info(">>>> BatchScheduler: bestProductJob 실행 시작...");
            jobLauncher.run(
                    bestProductJob,
                    new JobParametersBuilder()
                            .addLocalDateTime("runDateTime", LocalDateTime.now())
                            .toJobParameters()
            );

            log.info("<<<< BatchScheduler: bestProductJob 실행 완료.");

        } catch (Exception e) {
            
            log.error("BatchScheduler: bestProductJob 실행 중 오류 발생", e);

        }
    }
} 