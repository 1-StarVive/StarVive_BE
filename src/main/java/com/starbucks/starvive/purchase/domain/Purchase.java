package com.starbucks.starvive.purchase.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
public class Purchase extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID purchaseId;
}
