package com.starbucks.starvive.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequiredInfo {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productRequiredInfoId;

    private String type;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

}

