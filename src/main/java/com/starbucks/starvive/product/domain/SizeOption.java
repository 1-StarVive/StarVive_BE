package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class SizeOption extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
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
