package com.starbucks.starvive.category.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class FilterCategory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID filterCategoryId;

    private String filterValue;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID categoryId;

    @Enumerated(EnumType.STRING)
    private FilterType filterType;
}
