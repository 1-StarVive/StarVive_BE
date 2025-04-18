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
public class BestProduct extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID bestProductId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false)
    private Integer bestRank;

    @Builder
    public BestProduct(UUID productId, Integer bestRank) {
        this.productId = productId;
        this.bestRank = bestRank;
    }
} 