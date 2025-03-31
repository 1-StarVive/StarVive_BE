package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class LimitedEdition extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID limitedEditionId;

    @Column(nullable = false)
    private LocalDate saleStartAt;

    @Column(nullable = false)
    private LocalDate saleEndAt;

    private String productId;

    @Enumerated(EnumType.STRING)
    private LimitedEditionStatus limitedEditionStatus;
}
