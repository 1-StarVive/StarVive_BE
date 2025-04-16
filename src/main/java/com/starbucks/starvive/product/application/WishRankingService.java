package com.starbucks.starvive.product.application;

import com.starbucks.starvive.product.dto.WishCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishRankingService {

//    private final RedisTemplate<String, String> redisTemplate;
//    private static final String WISH_KEY = "wish_count";
//
//    // 찜 시 호출
//    public void incrementWish(UUID productId) {
//        redisTemplate.opsForZSet().incrementScore(WISH_KEY, productId.toString(), 1.0);
//    }
//
//    // 상위 N개 찜 랭킹 상품 ?
//    public List<WishCountDto> getTopWishes(int topN) {
//        Set<ZSetOperations.TypedTuple<String>> topSet = redisTemplate.opsForZSet()
//                .reverseRangeWithScores(WISH_KEY, 0, topN - 1);
//
//        if (topSet == null) return List.of();
//
//        return topSet.stream()
//                .map(tuple -> new WishCountDto(UUID.fromString(tuple.getValue()), tuple.getScore().longValue()))
//                .toList();
//    }

}
