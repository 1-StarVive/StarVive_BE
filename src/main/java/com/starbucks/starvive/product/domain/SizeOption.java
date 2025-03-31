package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SizeOption {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID sizeId;

    @Column(nullable = false)
    private String itemSize;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Builder
    public SizeOption(UUID sizeId, String itemSize, Capacity capacity) {
        this.sizeId = sizeId;
        this.itemSize = itemSize;
        this.capacity = capacity;
    }
}
