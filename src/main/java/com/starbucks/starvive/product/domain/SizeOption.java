package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SizeOption extends BaseEntity {

    @Column(nullable = false)
    private String itemSize;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Builder
    public SizeOption(String itemSize, Capacity capacity) {
        
        this.itemSize = itemSize;
        this.capacity = capacity;
    }
}
