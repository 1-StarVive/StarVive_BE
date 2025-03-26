package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class CarvingOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID carvingId;

    @Enumerated(EnumType.STRING)
    private CarvingType carvingType;

    private String text; // 입력 문구

    private Integer maxLength; // 길이 제한
}
