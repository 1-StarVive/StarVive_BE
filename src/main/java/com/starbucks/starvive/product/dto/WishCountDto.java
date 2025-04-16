package com.starbucks.starvive.product.dto;

import java.util.UUID;

//배치 내부 전달용 DTO - Redis ZSet에서 조회된 찜 수 집계 정보

public record WishCountDto(UUID productId, long wishCount) {

}
