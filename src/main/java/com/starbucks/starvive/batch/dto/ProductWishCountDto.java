package com.starbucks.starvive.batch.dto;

import java.util.UUID;

/**
 * 배치 작업에서 상품 ID와 찜 개수를 전달하기 위한 DTO
 */
public record ProductWishCountDto(UUID productId, long wishCount) {
} 