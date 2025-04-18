package com.starbucks.starvive.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TruncateTempTableListener implements JobExecutionListener {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        log.info("{} Job 시작 전: 임시 테이블 (temp_product_wish_count) 데이터 삭제 시도...", jobName);
        
        // Re-add the try-catch block (using DELETE)
        try {
            int deletedRows = jdbcTemplate.update("DELETE FROM temp_product_wish_count"); 
            log.info("임시 테이블 (temp_product_wish_count) 데이터 삭제 완료 ({} rows deleted).", deletedRows);
        } catch (Exception e) {
           log.error("임시 테이블 (temp_product_wish_count) 삭제 중 오류 발생", e);
           // Consider adding logic to fail the job if deletion fails
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Job 완료 후 필요한 작업이 있다면 여기에 추가
        String jobName = jobExecution.getJobInstance().getJobName();
        log.info("{} Job 완료. Status: {}", jobName, jobExecution.getStatus());
    }
} 