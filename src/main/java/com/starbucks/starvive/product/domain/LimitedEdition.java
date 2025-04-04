package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
@Entity
@Getter
@NoArgsConstructor
public class LimitedEdition extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID limitedEditionId;

    @Column(nullable = false)
    private LocalDate saleStartAt;

    @Column(nullable = false)
    private LocalDate saleEndAt;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Enumerated(EnumType.STRING)
    private LimitedEditionStatus limitedEditionStatus;
}
