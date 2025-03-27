package com.starbucks.starvive.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimitedEdition {

    @Id
    @GeneratedValue

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID limitedEditionId;

    private LocalDateTime saleStartAt;

    private LocalDateTime saleEndAt;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;
}
