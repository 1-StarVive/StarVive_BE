package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class LimitedEdition {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID limitedEditionId;

    private LocalDate saleStartAt;

    private LocalDate saleEndAt;

    private String productId;

    @Enumerated(EnumType.STRING)
    private LimitedEditionStatus limitedEditionStatus;
}
