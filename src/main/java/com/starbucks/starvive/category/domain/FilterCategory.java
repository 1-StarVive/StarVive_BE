package com.starbucks.starvive.category.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
