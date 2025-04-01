package com.starbucks.starvive.purchase.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Purchase extends BaseEntity {

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;
    
}
