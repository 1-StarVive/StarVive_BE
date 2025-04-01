package com.starbucks.starvive.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CursorPage<T> {

    private List<T> content;
    private Long nextCursor;
    private Long prevCursor;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer pageSize;
    private Integer page;

    public boolean hasNext() { return nextCursor != null; }

    public boolean hasBefore() { return hasPrevious; }

    @Builder
    public CursorPage(
            List<T> content,
            Long nextCursor,
            Long prevCursor,
            Boolean hasNext,
            Boolean hasPrevious,
            Integer pageSize,
            Integer page
    ) {
        this.content = content;
        this.nextCursor = nextCursor;
        this.prevCursor = prevCursor;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.pageSize = pageSize;
        this.page = page;
    }
}
