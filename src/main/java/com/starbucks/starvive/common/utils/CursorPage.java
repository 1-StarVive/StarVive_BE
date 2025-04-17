package com.starbucks.starvive.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CursorPage<T> {

    private List<T> content;
    private String nextCursor;
    private Boolean hasNext;
    private Integer pageSize;

    @Builder
    public CursorPage(
            List<T> content,
            String nextCursor,
            Boolean hasNext,
            Integer pageSize
    ) {
        this.content = content;
        this.nextCursor = nextCursor;
        this.hasNext = hasNext;
        this.pageSize = pageSize;
    }

    public boolean hasNext() {
        return nextCursor != null;
    }
}
