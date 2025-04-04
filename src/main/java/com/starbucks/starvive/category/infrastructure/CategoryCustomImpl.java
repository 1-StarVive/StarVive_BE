package com.starbucks.starvive.category.infrastructure;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.starbucks.starvive.category.domain.*;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponse;
import com.starbucks.starvive.category.dto.out.MiddleWithBottomCategoryResponse;
import com.starbucks.starvive.category.dto.out.TopWithMiddleBottomCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CategoryCustomImpl implements CategoryCustomRepository {

    private final JPAQueryFactory query;

    QTopCategory top = QTopCategory.topCategory;
    QMiddleCategory middle = QMiddleCategory.middleCategory;
    QBottomCategory bottom = QBottomCategory.bottomCategory;

    @Override
    public List<TopWithMiddleBottomCategoryResponse> findCategoryByTopCategoryId(UUID topCategoryId) {

        List<Tuple> results = query
                .select(top, middle, bottom)
                .from(top)
                .leftJoin(middle).on(middle.topCategoryId.eq(top.topCategoryId))
                .leftJoin(bottom).on(bottom.middleCategoryId.eq(middle.middleCategoryId))
                .where(top.topCategoryId.eq(topCategoryId))
                .fetch();

        Map<UUID, TopWithMiddleBottomCategoryResponse> topMap = new LinkedHashMap<>();

        for (Tuple row : results) {
            TopCategory topEntity = row.get(top);
            MiddleCategory midEntity = row.get(middle);
            BottomCategory botEntity = row.get(bottom);

            // topCategoryId 없으면 추가 후 중간/하위 출력
            topMap.putIfAbsent(topEntity.getTopCategoryId(),
                    TopWithMiddleBottomCategoryResponse.builder()
                            .topCategoryId(topEntity.getTopCategoryId())
                            .name(topEntity.getName())
                            .thumbImageUrl(topEntity.getThumbImageUrl())
                            .thumbAlt(topEntity.getThumbAlt())
                            .middleCategories(new ArrayList<>())
                            .build()
            );

            // topCategoryId 있으면 중간/하위만 출력
            TopWithMiddleBottomCategoryResponse topDto = topMap.get(topEntity.getTopCategoryId());

            if (midEntity != null) {
                List<MiddleWithBottomCategoryResponse> middleList = topDto.getMiddleCategories();

                MiddleWithBottomCategoryResponse middleDto = middleList.stream()
                        .filter(m -> m.getMiddleCategoryId().equals(midEntity.getMiddleCategoryId()))
                        .findFirst()
                        .orElseGet(() -> {
                            MiddleWithBottomCategoryResponse m = MiddleWithBottomCategoryResponse.builder()
                                    .middleCategoryId(midEntity.getMiddleCategoryId())
                                    .name(midEntity.getName())
                                    .bottomCategories(new ArrayList<>())
                                    .build();
                            middleList.add(m);
                            return m;
                        });

                if (botEntity != null && middleDto.getBottomCategories().stream()
                        .noneMatch(b -> b.getBottomCategoryId().equals(botEntity.getBottomCategoryId()))) {
                    middleDto.getBottomCategories().add(BottomCategoryResponse.from(botEntity));
                }
            }
        }

        return new ArrayList<>(topMap.values());
    }

}

