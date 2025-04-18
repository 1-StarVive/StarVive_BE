package com.starbucks.starvive.batch; // 또는 다른 적절한 패키지

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

    private final JobLauncher jobLauncher; // Job을 실행시키기 위한 JobLauncher
    private final Job bestProductJob;      // BatchConfig에서 정의한 Job Bean 주입

    // 매일 자정에 bestProductJob 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void runBestProductJob() {
        try {
            log.info(">>>> BatchScheduler: bestProductJob 실행 시작...");
            // Job 실행 시 JobParameters를 넘겨야 함 (동일 Job이라도 파라미터가 다르면 다른 실행으로 간주)
            // 매번 다른 파라미터를 주기 위해 현재 시각 사용
            jobLauncher.run(
                    bestProductJob,
                    new JobParametersBuilder()
                            .addLocalDateTime("runDateTime", LocalDateTime.now()) // 실행 시각을 파라미터로 추가
                            .toJobParameters()
            );
            log.info("<<<< BatchScheduler: bestProductJob 실행 완료.");
        } catch (Exception e) {
            log.error("BatchScheduler: bestProductJob 실행 중 오류 발생", e);
        }
    }
} 