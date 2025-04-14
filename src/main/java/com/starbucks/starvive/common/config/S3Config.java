package com.starbucks.starvive.common.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    // application.yml의 cloud.aws.region.static 또는 환경 변수 AWS_REGION 사용
    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        // DefaultAWSCredentialsProviderChain 사용: 환경 변수, 시스템 속성, 프로파일 등에서 자격 증명 자동 검색
        // Jenkins에서 주입된 AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY 환경 변수를 자동으로 사용함
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider) // 자격 증명 자동 감지
                .withRegion(region) // 리전 설정
                .build();
    }
}
